package ru.opencore.patcher

import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import org.liquibase.schema.DatabaseChangeLog
import java.io.FileInputStream
import javax.xml.bind.JAXBContext
import java.util.Properties
import java.util.ArrayList

fun getPath(): String {
	var environment = ProcessBuilder().environment();

	var path = environment.get("Path");
	if (path == null) {
		path = environment.get("PATH");
	}
	if (path == null) {
		path = environment.get("path");
	}
	if (path == null) {
		throw IOException("Cannot find path variable in environment");
	}

	return path;
}

open class Config {
	var path = getPath()
	var liquiBaseDbPropertiesPath: String = "liquibase.properties"
	var patchDir: String = Paths.get(".").toAbsolutePath().normalize().toString() + "/meta"
	var liquiBasePatchFile: String = patchDir + "/meta.xml"
	var type: DBType = DBType.POSTGRES
	var props: Properties = Properties()
	var url: String = ""
	var username: String = ""
	var password: String = ""
	var changeLog: DatabaseChangeLog = DatabaseChangeLog()
	var ckIds: List<String> = ArrayList<String>()
	var page: List<String> = ArrayList<String>()
	var providers: List<String> = ArrayList<String>()
	open fun setDbFilePath(path: String): Config {
		this.liquiBaseDbPropertiesPath = path;
		return this
	}

	open fun setPatchDir(path: String, nameXml: String = "meta.xml"): Config {
		this.patchDir = path;
		this.liquiBasePatchFile = patchDir + "/${nameXml}";
		return this
	}

	open fun setType(type: String): Config {
		this.type = typeDBOf(type);
		return this
	}
	
	open fun setCkIds(ids: String): Config {
		this.ckIds = ids.split(",").toList()
		return this
	}
	
	open fun setPage(page: String): Config {
		this.page = page.split(",").toList()
		return this
	}

	open fun setProviders(providers: String): Config {
		this.providers = providers.split(",").toList()
		return this
	}
	
	open fun init(): Config {
		var dbProperty = getPath(liquiBaseDbPropertiesPath)
		if (dbProperty == null) {
			throw IOException("Not found ${liquiBaseDbPropertiesPath}")
		}
		var inputStream = FileInputStream(dbProperty)
        props.load(inputStream);
		inputStream.close()
		url = props.get("url") as String
		username = props.get("username") as String
		password = props.get("password") as String
		var patchDirFile = getPath(patchDir)
		if (patchDirFile == null) {
			File(patchDir).mkdirs()
		}
		var fLiquiBasePatchFile = File(liquiBasePatchFile)
		if (fLiquiBasePatchFile.exists()) {
			var jaxbContext = JAXBContext.newInstance(DatabaseChangeLog::class.java);
            var jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			var inStream = FileInputStream(fLiquiBasePatchFile);
            changeLog = jaxbUnmarshaller.unmarshal( inStream ) as DatabaseChangeLog;
			inStream.close();
		}
		return this
	}

	open fun getPath(fileName: String): String? {
		var file = File(fileName);
		if (file.exists()) {
			return file.getAbsolutePath();
		}
		file = File(Paths.get(".").toAbsolutePath().normalize().toString(), fileName);
		if (file.exists()) {
			return file.getAbsolutePath();
		}
		for (dir in path.split("[:;]")) {
			file = File(dir, fileName);
			if (file.exists()) {
				return file.getAbsolutePath();
			}
		}
		return null;
	}
}
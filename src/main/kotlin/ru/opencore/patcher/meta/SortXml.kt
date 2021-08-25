package ru.opencore.patcher.meta

import java.util.stream.Stream
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.Files
import java.util.stream.Collectors
import org.liquibase.schema.DatabaseChangeLog
import org.liquibase.schema.DatabaseChangeLog.Include
import javax.xml.bind.JAXBContext
import java.io.FileOutputStream
import javax.xml.bind.Marshaller
import java.util.ArrayList

fun createMarshaller(): Marshaller {
	var jaxbContext = JAXBContext.newInstance(DatabaseChangeLog::class.java);
	var marshaller = jaxbContext.createMarshaller();
	marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
	marshaller.setProperty(
		Marshaller.JAXB_SCHEMA_LOCATION,
		"http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.1.xsd"
	);
	return marshaller
}

fun sortXmlQuery(dir: String) {
	var marshaller = createMarshaller();
	var changeLog = DatabaseChangeLog();
	changeLog.getChangeSetOrIncludeOrIncludeAll().addAll(Files.walk(Paths.get(dir))
		.filter { file -> file.getFileName().toString().toLowerCase().endsWith(".sql") }
		.sorted { o1, o2 ->
			var name1 = o1.getFileName().toString().toLowerCase();
			var name2 = o2.getFileName().toString().toLowerCase();
			if ((name1.startsWith("provider_") && name2.startsWith("provider_")) ||
				(!name1.startsWith("provider_") && !name2.startsWith("provider_"))
			) {
				o1.getFileName().compareTo(o2.getFileName())
			} else if (name1.startsWith("provider_")) {
				-1
			} else {
				1
			}
		}
		.map { file ->
			var inc = Include();
			inc.setFile(file.getFileName().toString());
			inc.setRelativeToChangelogFile("true");
			inc
		}
		.collect(Collectors.toList()));
	var queryXml = FileOutputStream("${dir}/query.xml");
	marshaller.marshal(changeLog, queryXml);
}

data class PagePair(
	val file: Path,
	val data: String,
	val guid: String,
	var parent: String? = null,
	var isChild: Boolean = false
)

val regex = Regex(":Page_([^\\s]+)", setOf(RegexOption.IGNORE_CASE, RegexOption.MULTILINE))

fun readFilePage(file: Path): PagePair {
	var str = Files.lines(file).filter { line ->
		line.indexOf("s_mt.t_page ") > -1 || line.indexOf("--changeset") > -1
	}
		.collect(Collectors.joining("\n"))
	return PagePair(file.getFileName(), str, regex.findAll(str).map { it.groupValues[1] }.joinToString(), null, false)
}

fun treePage(all: List<PagePair>, parent: String?): List<PagePair> {
	var res = ArrayList<PagePair>();
	all
		.stream()
		.filter { p -> p.parent == parent }
		.forEach { p ->
			res.add(p);
			if (p.isChild) {
				res.addAll(treePage(all, p.guid))
			}
		}
	return res
}

fun sortXmlPage(dir: String) {
	var marshaller = createMarshaller();
	var changeLog = DatabaseChangeLog();
	var files = Files.walk(Paths.get(dir))
		.filter { file -> file.getFileName().toString().toLowerCase().endsWith(".sql") }
		.map { file -> readFilePage(file) }
		.sorted { o1, o2 -> o1.file.compareTo(o2.file) }
		.collect(Collectors.toList());
	files.stream().forEach { pair ->
		files.stream().filter { p ->
			p.guid != pair.guid && p.data.indexOf("'${pair.guid}'") > -1
		}
			.forEach { p ->
				p.parent = pair.guid;
				pair.isChild = true;
			}
	}
	changeLog.getChangeSetOrIncludeOrIncludeAll().addAll(
		treePage(files, null)
			.stream()
			.map { pair ->
				var inc = Include();
				inc.setFile(pair.file.toString());
				inc.setRelativeToChangelogFile("true");
				inc
			}
			.collect(Collectors.toList()));
	var queryXml = FileOutputStream("${dir}/page.xml");
	marshaller.marshal(changeLog, queryXml);
}

fun sortXmlLocalization(dir: String) {
	var marshaller = createMarshaller();
	var changeLog = DatabaseChangeLog();
	changeLog.getChangeSetOrIncludeOrIncludeAll().addAll(Files.walk(Paths.get(dir))
		.filter { file -> file.getFileName().toString().toLowerCase().endsWith(".sql") }
		.sorted { o1, o2 -> o1.getFileName().compareTo(o2.getFileName()) }
		.map { file ->
			var inc = Include();
			inc.setFile(file.getFileName().toString());
			inc.setRelativeToChangelogFile("true");
			inc
		}
		.collect(Collectors.toList()));
	var queryXml = FileOutputStream("${dir}/localization.xml");
	marshaller.marshal(changeLog, queryXml);
}

fun sortMeta(dir: String, name: String): Boolean {
	var marshaller = createMarshaller();
	var changeLog = DatabaseChangeLog();
	var files = Files.list(Paths.get(dir))
		.sorted { o1, o2 ->
			o1.getFileName().toString().toLowerCase().compareTo(o2.getFileName().toString().toLowerCase())
		}
		.filter { file -> !file.toFile().isDirectory() || (file.toFile().isDirectory() && file.resolve("${file.getFileName().toString()}.xml").toFile().exists()) }
		.map { file ->
			if (!file.toFile().isDirectory()) {
				file.getFileName()
			} else {
				file.getFileName().resolve("${file.getFileName().toString()}.xml")
			}
		}
		.collect(Collectors.toList())
	changeLog.getChangeSetOrIncludeOrIncludeAll().addAll(
		files.stream()
			.filter { file -> file.getFileName().toString().toLowerCase().startsWith("view_")}
			.map { file ->
				var inc = Include();
				inc.setFile(file.toString().replace("\\","/"));
				inc.setRelativeToChangelogFile("true");
				inc
			}
			.collect(Collectors.toList())
	);
	changeLog.getChangeSetOrIncludeOrIncludeAll().addAll(
		files.stream()
			.filter { file -> file.getFileName().toString().toLowerCase() != "page.xml" &&
					!file.getFileName().toString().toLowerCase().startsWith("page_") &&
					!file.getFileName().toString().toLowerCase().startsWith("view_") &&
					file.getFileName().toString().toLowerCase() != "scripts.sql" &&
					file.getFileName().toString().toLowerCase() != "${name}.xml" }
			.map { file ->
				var inc = Include();
				inc.setFile(file.toString().replace("\\","/"));
				inc.setRelativeToChangelogFile("true");
				inc
			}
			.collect(Collectors.toList())
	);
	changeLog.getChangeSetOrIncludeOrIncludeAll().addAll(
		files.stream()
			.filter { file -> file.getFileName().toString().toLowerCase() == "page.xml" || file.getFileName().toString().toLowerCase().startsWith("page_") }
			.map { file ->
				var inc = Include();
				inc.setFile(file.toString().replace("\\","/"));
				inc.setRelativeToChangelogFile("true");
				inc
			}
			.collect(Collectors.toList())
	);
	changeLog.getChangeSetOrIncludeOrIncludeAll().addAll(
		files.stream()
			.filter { file -> file.getFileName().toString().toLowerCase() == "scripts.sql" }
			.map { file ->
				var inc = Include();
				inc.setFile(file.toString().replace("\\","/"));
				inc.setRelativeToChangelogFile("true");
				inc
			}
			.collect(Collectors.toList())
	);
	var queryXml = FileOutputStream("${dir}/${name}.xml");
	marshaller.marshal(changeLog, queryXml);
	return true
}

package ru.opencore.patcher

import org.apache.commons.cli.*
import java.util.Properties
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin
import ru.opencore.patcher.meta.*
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import org.jdbi.v3.oracle12.OracleReturning
import java.sql.DriverManager
import org.jdbi.v3.postgres.PostgresPlugin
import java.nio.file.Paths
import ru.opencore.patcher.auth.createPatchAccount
import ru.opencore.patcher.auth.createPatchRole
import ru.opencore.patcher.auth.createPatchAction
import ru.opencore.patcher.auth.createPatchDInfo

var config = Config()

fun main(args: Array<String>) {

	var options = Options();
	options.addOption(Option.builder("h").longOpt("help").desc("Print this message!").build());
	options.addOption(
		Option.builder("c").longOpt("create").hasArg(true).desc("Required, create patch: class,object,page,query,syssetting,messages,modules,lang,auth").argName(
			"type"
		).build()
	);
	options.addOption(
		Option.builder("p").longOpt("property").hasArg(true).desc("DB property").argName(
			"file"
		).build()
	);
	options.addOption(
		Option.builder("page").longOpt("page").hasArg(true).desc("Id Page").argName(
			"page"
		).build()
	);
	options.addOption(
		Option.builder("d").longOpt("patchDir").hasArg(true).desc("Path dir: ./meta").argName(
			"file"
		).build()
	);

	options.addOption(
		Option.builder("t").longOpt("typeDb").hasArg(true).desc("Type patch: postgresql,oracle\r\nDefault postgresql").argName(
			"type"
		).build()
	);

	options.addOption(
		Option.builder("o").longOpt("object_ids").hasArg(true).desc("ID parent object").argName(
			"ids"
		).build()
	);

	options.addOption(
		Option.builder("qp").longOpt("query-providers").hasArg(true).desc("Name providers").argName(
			"providername"
		).build()
	);

	var parser = DefaultParser();
	try {
		var cmd = parser.parse(options, args);
		if (cmd.hasOption("h")) {
			HelpFormatter().printHelp("core-patcher", options);
			System.exit(0);
		}
		if (!cmd.hasOption("c")) {
			HelpFormatter().printHelp("core-patcher", options);
			System.exit(0);
		}
		if (cmd.getOptionValue("c") == "auth") {
			config.setPatchDir(Paths.get(".").toAbsolutePath().normalize().toString() + "/auth", "auth.xml");
		}
		if (cmd.hasOption("p")) {
			config.setDbFilePath(cmd.getOptionValue("p"));
		}
		if (cmd.hasOption("d")) {
			config.setPatchDir(cmd.getOptionValue("d"));
		}
		if (cmd.hasOption("t")) {
			config.setType(cmd.getOptionValue("t"));
		}
		if (cmd.hasOption("o")) {
			config.setCkIds(cmd.getOptionValue("o"));
		}
		if (cmd.hasOption("qp")) {
			config.setProviders(cmd.getOptionValue("qp"));
		}
		if (cmd.hasOption("page")) {
			config.setPage(cmd.getOptionValue("page"));
		}
		config.init();
		println(config.props.toString())
		var jdbi = Jdbi.create(config.url, config.username, config.password)
		var bdQuery: MDB = MPostgres()
		if (config.url.startsWith("jdbc:postgresql")) {
			bdQuery = MPostgres()
			jdbi.installPlugin(PostgresPlugin())
		}
		if (config.url.startsWith("jdbc:oracle")) {
			bdQuery = MOracle()
		}
		jdbi.installPlugin(SqlObjectPlugin())
		jdbi.installPlugin(KotlinPlugin())
		jdbi.installPlugin(KotlinSqlObjectPlugin())

		when (cmd.getOptionValue("c")) {
			"class" -> createClassPatch(jdbi.open(), config, bdQuery) && sortMeta(config.patchDir, "meta")
			"query" -> createQueryPatch(jdbi.open(), config, bdQuery) && sortMeta(config.patchDir, "meta")
			"object" -> createObjectPatch(jdbi.open(), config, bdQuery) && sortMeta(config.patchDir, "meta")
			"page" -> createPagePatch(jdbi.open(), config, bdQuery) && sortMeta(config.patchDir, "meta")
			"syssetting" -> createSysSettingsPatch(jdbi.open(), config, bdQuery) && sortMeta(config.patchDir, "meta")
			"messages" -> createMessagesPatch(jdbi.open(), config, bdQuery) && sortMeta(config.patchDir, "meta")
			"modules" -> createModulesPatch(jdbi.open(), config, bdQuery) && sortMeta(config.patchDir, "meta")
			"lang" -> createLangPatch(jdbi.open(), config, bdQuery) && sortMeta(config.patchDir, "meta")
			"view" -> createViewPatch(jdbi.open(), config, bdQuery) && sortMeta(config.patchDir, "meta")
			"auth" -> createPatchDInfo(jdbi.open(), config, bdQuery) && createPatchAction(
				jdbi.open(),
				config,
				bdQuery
			) && createPatchRole(jdbi.open(), config, bdQuery) && createPatchAccount(jdbi.open(), config, bdQuery) && sortMeta(config.patchDir, "auth")
			else -> HelpFormatter().printHelp("core-patcher", options)
		}
	} catch (e: ParseException) {
		e.printStackTrace()
		HelpFormatter().printHelp("core-patcher", options);
	}

	println("Finish!");

}

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.08.26 at 08:26:28 PM PST 
//


package org.liquibase.schema;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.liquibase.schema package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Comment_QNAME = new QName("http://www.liquibase.org/xml/ns/dbchangelog", "comment");
    private final static QName _LoadUpdateDataColumn_QNAME = new QName("http://www.liquibase.org/xml/ns/dbchangelog", "column");
    private final static QName _DeleteWhere_QNAME = new QName("http://www.liquibase.org/xml/ns/dbchangelog", "where");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.liquibase.schema
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CustomPrecondition }
     * 
     */
    public CustomPrecondition createCustomPrecondition() {
        return new CustomPrecondition();
    }

    /**
     * Create an instance of {@link CustomChange }
     * 
     */
    public CustomChange createCustomChange() {
        return new CustomChange();
    }

    /**
     * Create an instance of {@link AddColumn }
     * 
     */
    public AddColumn createAddColumn() {
        return new AddColumn();
    }

    /**
     * Create an instance of {@link ExecuteCommand }
     * 
     */
    public ExecuteCommand createExecuteCommand() {
        return new ExecuteCommand();
    }

    /**
     * Create an instance of {@link LoadUpdateData }
     * 
     */
    public LoadUpdateData createLoadUpdateData() {
        return new LoadUpdateData();
    }

    /**
     * Create an instance of {@link LoadData }
     * 
     */
    public LoadData createLoadData() {
        return new LoadData();
    }

    /**
     * Create an instance of {@link DatabaseChangeLog }
     * 
     */
    public DatabaseChangeLog createDatabaseChangeLog() {
        return new DatabaseChangeLog();
    }

    /**
     * Create an instance of {@link DatabaseChangeLog.ChangeSet }
     * 
     */
    public DatabaseChangeLog.ChangeSet createDatabaseChangeLogChangeSet() {
        return new DatabaseChangeLog.ChangeSet();
    }

    /**
     * Create an instance of {@link DatabaseChangeLog.ChangeSet.ModifySql }
     * 
     */
    public DatabaseChangeLog.ChangeSet.ModifySql createDatabaseChangeLogChangeSetModifySql() {
        return new DatabaseChangeLog.ChangeSet.ModifySql();
    }

    /**
     * Create an instance of {@link AddUniqueConstraint }
     * 
     */
    public AddUniqueConstraint createAddUniqueConstraint() {
        return new AddUniqueConstraint();
    }

    /**
     * Create an instance of {@link AlterSequence }
     * 
     */
    public AlterSequence createAlterSequence() {
        return new AlterSequence();
    }

    /**
     * Create an instance of {@link DropNotNullConstraint }
     * 
     */
    public DropNotNullConstraint createDropNotNullConstraint() {
        return new DropNotNullConstraint();
    }

    /**
     * Create an instance of {@link AddDefaultValue }
     * 
     */
    public AddDefaultValue createAddDefaultValue() {
        return new AddDefaultValue();
    }

    /**
     * Create an instance of {@link RenameTable }
     * 
     */
    public RenameTable createRenameTable() {
        return new RenameTable();
    }

    /**
     * Create an instance of {@link Dbms }
     * 
     */
    public Dbms createDbms() {
        return new Dbms();
    }

    /**
     * Create an instance of {@link DropAllForeignKeyConstraints }
     * 
     */
    public DropAllForeignKeyConstraints createDropAllForeignKeyConstraints() {
        return new DropAllForeignKeyConstraints();
    }

    /**
     * Create an instance of {@link DropUniqueConstraint }
     * 
     */
    public DropUniqueConstraint createDropUniqueConstraint() {
        return new DropUniqueConstraint();
    }

    /**
     * Create an instance of {@link DropView }
     * 
     */
    public DropView createDropView() {
        return new DropView();
    }

    /**
     * Create an instance of {@link DropColumn }
     * 
     */
    public DropColumn createDropColumn() {
        return new DropColumn();
    }

    /**
     * Create an instance of {@link Constraints }
     * 
     */
    public Constraints createConstraints() {
        return new Constraints();
    }

    /**
     * Create an instance of {@link DropIndex }
     * 
     */
    public DropIndex createDropIndex() {
        return new DropIndex();
    }

    /**
     * Create an instance of {@link DropDefaultValue }
     * 
     */
    public DropDefaultValue createDropDefaultValue() {
        return new DropDefaultValue();
    }

    /**
     * Create an instance of {@link RenameColumn }
     * 
     */
    public RenameColumn createRenameColumn() {
        return new RenameColumn();
    }

    /**
     * Create an instance of {@link CustomPrecondition.Param }
     * 
     */
    public CustomPrecondition.Param createCustomPreconditionParam() {
        return new CustomPrecondition.Param();
    }

    /**
     * Create an instance of {@link DropProcedure }
     * 
     */
    public DropProcedure createDropProcedure() {
        return new DropProcedure();
    }

    /**
     * Create an instance of {@link WhereParams }
     * 
     */
    public WhereParams createWhereParams() {
        return new WhereParams();
    }

    /**
     * Create an instance of {@link org.liquibase.schema.Param }
     * 
     */
    public org.liquibase.schema.Param createParam() {
        return new org.liquibase.schema.Param();
    }

    /**
     * Create an instance of {@link ExpectedQuotingStrategy }
     * 
     */
    public ExpectedQuotingStrategy createExpectedQuotingStrategy() {
        return new ExpectedQuotingStrategy();
    }

    /**
     * Create an instance of {@link DropForeignKeyConstraint }
     * 
     */
    public DropForeignKeyConstraint createDropForeignKeyConstraint() {
        return new DropForeignKeyConstraint();
    }

    /**
     * Create an instance of {@link DropSequence }
     * 
     */
    public DropSequence createDropSequence() {
        return new DropSequence();
    }

    /**
     * Create an instance of {@link CustomChange.Param }
     * 
     */
    public CustomChange.Param createCustomChangeParam() {
        return new CustomChange.Param();
    }

    /**
     * Create an instance of {@link AddColumn.Column }
     * 
     */
    public AddColumn.Column createAddColumnColumn() {
        return new AddColumn.Column();
    }

    /**
     * Create an instance of {@link ColumnExists }
     * 
     */
    public ColumnExists createColumnExists() {
        return new ColumnExists();
    }

    /**
     * Create an instance of {@link DropPrimaryKey }
     * 
     */
    public DropPrimaryKey createDropPrimaryKey() {
        return new DropPrimaryKey();
    }

    /**
     * Create an instance of {@link SqlCheck }
     * 
     */
    public SqlCheck createSqlCheck() {
        return new SqlCheck();
    }

    /**
     * Create an instance of {@link IndexExists }
     * 
     */
    public IndexExists createIndexExists() {
        return new IndexExists();
    }

    /**
     * Create an instance of {@link ExecuteCommand.Arg }
     * 
     */
    public ExecuteCommand.Arg createExecuteCommandArg() {
        return new ExecuteCommand.Arg();
    }

    /**
     * Create an instance of {@link CreateSequence }
     * 
     */
    public CreateSequence createCreateSequence() {
        return new CreateSequence();
    }

    /**
     * Create an instance of {@link ChangeSetExecuted }
     * 
     */
    public ChangeSetExecuted createChangeSetExecuted() {
        return new ChangeSetExecuted();
    }

    /**
     * Create an instance of {@link ViewExists }
     * 
     */
    public ViewExists createViewExists() {
        return new ViewExists();
    }

    /**
     * Create an instance of {@link LoadUpdateData.Column }
     * 
     */
    public LoadUpdateData.Column createLoadUpdateDataColumn() {
        return new LoadUpdateData.Column();
    }

    /**
     * Create an instance of {@link Stop }
     * 
     */
    public Stop createStop() {
        return new Stop();
    }

    /**
     * Create an instance of {@link TagDatabase }
     * 
     */
    public TagDatabase createTagDatabase() {
        return new TagDatabase();
    }

    /**
     * Create an instance of {@link SqlFile }
     * 
     */
    public SqlFile createSqlFile() {
        return new SqlFile();
    }

    /**
     * Create an instance of {@link AddForeignKeyConstraint }
     * 
     */
    public AddForeignKeyConstraint createAddForeignKeyConstraint() {
        return new AddForeignKeyConstraint();
    }

    /**
     * Create an instance of {@link MergeColumns }
     * 
     */
    public MergeColumns createMergeColumns() {
        return new MergeColumns();
    }

    /**
     * Create an instance of {@link CreateTable }
     * 
     */
    public CreateTable createCreateTable() {
        return new CreateTable();
    }

    /**
     * Create an instance of {@link org.liquibase.schema.Column }
     * 
     */
    public org.liquibase.schema.Column createColumn() {
        return new org.liquibase.schema.Column();
    }

    /**
     * Create an instance of {@link TableIsEmpty }
     * 
     */
    public TableIsEmpty createTableIsEmpty() {
        return new TableIsEmpty();
    }

    /**
     * Create an instance of {@link Insert }
     * 
     */
    public Insert createInsert() {
        return new Insert();
    }

    /**
     * Create an instance of {@link Update }
     * 
     */
    public Update createUpdate() {
        return new Update();
    }

    /**
     * Create an instance of {@link AddPrimaryKey }
     * 
     */
    public AddPrimaryKey createAddPrimaryKey() {
        return new AddPrimaryKey();
    }

    /**
     * Create an instance of {@link LoadData.Column }
     * 
     */
    public LoadData.Column createLoadDataColumn() {
        return new LoadData.Column();
    }

    /**
     * Create an instance of {@link DatabaseChangeLog.Property }
     * 
     */
    public DatabaseChangeLog.Property createDatabaseChangeLogProperty() {
        return new DatabaseChangeLog.Property();
    }

    /**
     * Create an instance of {@link DatabaseChangeLog.PreConditions }
     * 
     */
    public DatabaseChangeLog.PreConditions createDatabaseChangeLogPreConditions() {
        return new DatabaseChangeLog.PreConditions();
    }

    /**
     * Create an instance of {@link DatabaseChangeLog.Include }
     * 
     */
    public DatabaseChangeLog.Include createDatabaseChangeLogInclude() {
        return new DatabaseChangeLog.Include();
    }

    /**
     * Create an instance of {@link DatabaseChangeLog.IncludeAll }
     * 
     */
    public DatabaseChangeLog.IncludeAll createDatabaseChangeLogIncludeAll() {
        return new DatabaseChangeLog.IncludeAll();
    }

    /**
     * Create an instance of {@link TableExists }
     * 
     */
    public TableExists createTableExists() {
        return new TableExists();
    }

    /**
     * Create an instance of {@link Delete }
     * 
     */
    public Delete createDelete() {
        return new Delete();
    }

    /**
     * Create an instance of {@link ChangeLogPropertyDefined }
     * 
     */
    public ChangeLogPropertyDefined createChangeLogPropertyDefined() {
        return new ChangeLogPropertyDefined();
    }

    /**
     * Create an instance of {@link Sql }
     * 
     */
    public Sql createSql() {
        return new Sql();
    }

    /**
     * Create an instance of {@link RunningAs }
     * 
     */
    public RunningAs createRunningAs() {
        return new RunningAs();
    }

    /**
     * Create an instance of {@link Not }
     * 
     */
    public Not createNot() {
        return new Not();
    }

    /**
     * Create an instance of {@link And }
     * 
     */
    public And createAnd() {
        return new And();
    }

    /**
     * Create an instance of {@link Or }
     * 
     */
    public Or createOr() {
        return new Or();
    }

    /**
     * Create an instance of {@link SequenceExists }
     * 
     */
    public SequenceExists createSequenceExists() {
        return new SequenceExists();
    }

    /**
     * Create an instance of {@link ForeignKeyConstraintExists }
     * 
     */
    public ForeignKeyConstraintExists createForeignKeyConstraintExists() {
        return new ForeignKeyConstraintExists();
    }

    /**
     * Create an instance of {@link PrimaryKeyExists }
     * 
     */
    public PrimaryKeyExists createPrimaryKeyExists() {
        return new PrimaryKeyExists();
    }

    /**
     * Create an instance of {@link RowCount }
     * 
     */
    public RowCount createRowCount() {
        return new RowCount();
    }

    /**
     * Create an instance of {@link CreateProcedure }
     * 
     */
    public CreateProcedure createCreateProcedure() {
        return new CreateProcedure();
    }

    /**
     * Create an instance of {@link ModifyDataType }
     * 
     */
    public ModifyDataType createModifyDataType() {
        return new ModifyDataType();
    }

    /**
     * Create an instance of {@link AddLookupTable }
     * 
     */
    public AddLookupTable createAddLookupTable() {
        return new AddLookupTable();
    }

    /**
     * Create an instance of {@link CreateView }
     * 
     */
    public CreateView createCreateView() {
        return new CreateView();
    }

    /**
     * Create an instance of {@link DropTable }
     * 
     */
    public DropTable createDropTable() {
        return new DropTable();
    }

    /**
     * Create an instance of {@link Rollback }
     * 
     */
    public Rollback createRollback() {
        return new Rollback();
    }

    /**
     * Create an instance of {@link RenameView }
     * 
     */
    public RenameView createRenameView() {
        return new RenameView();
    }

    /**
     * Create an instance of {@link CreateIndex }
     * 
     */
    public CreateIndex createCreateIndex() {
        return new CreateIndex();
    }

    /**
     * Create an instance of {@link AddNotNullConstraint }
     * 
     */
    public AddNotNullConstraint createAddNotNullConstraint() {
        return new AddNotNullConstraint();
    }

    /**
     * Create an instance of {@link AddAutoIncrement }
     * 
     */
    public AddAutoIncrement createAddAutoIncrement() {
        return new AddAutoIncrement();
    }

    /**
     * Create an instance of {@link ColumnType }
     * 
     */
    public ColumnType createColumnType() {
        return new ColumnType();
    }

    /**
     * Create an instance of {@link DatabaseChangeLog.ChangeSet.ValidCheckSum }
     * 
     */
    public DatabaseChangeLog.ChangeSet.ValidCheckSum createDatabaseChangeLogChangeSetValidCheckSum() {
        return new DatabaseChangeLog.ChangeSet.ValidCheckSum();
    }

    /**
     * Create an instance of {@link DatabaseChangeLog.ChangeSet.PreConditions }
     * 
     */
    public DatabaseChangeLog.ChangeSet.PreConditions createDatabaseChangeLogChangeSetPreConditions() {
        return new DatabaseChangeLog.ChangeSet.PreConditions();
    }

    /**
     * Create an instance of {@link DatabaseChangeLog.ChangeSet.ModifySql.Replace }
     * 
     */
    public DatabaseChangeLog.ChangeSet.ModifySql.Replace createDatabaseChangeLogChangeSetModifySqlReplace() {
        return new DatabaseChangeLog.ChangeSet.ModifySql.Replace();
    }

    /**
     * Create an instance of {@link DatabaseChangeLog.ChangeSet.ModifySql.RegExpReplace }
     * 
     */
    public DatabaseChangeLog.ChangeSet.ModifySql.RegExpReplace createDatabaseChangeLogChangeSetModifySqlRegExpReplace() {
        return new DatabaseChangeLog.ChangeSet.ModifySql.RegExpReplace();
    }

    /**
     * Create an instance of {@link DatabaseChangeLog.ChangeSet.ModifySql.Prepend }
     * 
     */
    public DatabaseChangeLog.ChangeSet.ModifySql.Prepend createDatabaseChangeLogChangeSetModifySqlPrepend() {
        return new DatabaseChangeLog.ChangeSet.ModifySql.Prepend();
    }

    /**
     * Create an instance of {@link DatabaseChangeLog.ChangeSet.ModifySql.Append }
     * 
     */
    public DatabaseChangeLog.ChangeSet.ModifySql.Append createDatabaseChangeLogChangeSetModifySqlAppend() {
        return new DatabaseChangeLog.ChangeSet.ModifySql.Append();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.liquibase.org/xml/ns/dbchangelog", name = "comment")
    public JAXBElement<String> createComment(String value) {
        return new JAXBElement<String>(_Comment_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadUpdateData.Column }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.liquibase.org/xml/ns/dbchangelog", name = "column", scope = LoadUpdateData.class)
    public JAXBElement<LoadUpdateData.Column> createLoadUpdateDataColumn(LoadUpdateData.Column value) {
        return new JAXBElement<LoadUpdateData.Column>(_LoadUpdateDataColumn_QNAME, LoadUpdateData.Column.class, LoadUpdateData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadData.Column }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.liquibase.org/xml/ns/dbchangelog", name = "column", scope = LoadData.class)
    public JAXBElement<LoadData.Column> createLoadDataColumn(LoadData.Column value) {
        return new JAXBElement<LoadData.Column>(_LoadUpdateDataColumn_QNAME, LoadData.Column.class, LoadData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.liquibase.org/xml/ns/dbchangelog", name = "where", scope = Delete.class)
    public JAXBElement<Object> createDeleteWhere(Object value) {
        return new JAXBElement<Object>(_DeleteWhere_QNAME, Object.class, Delete.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.liquibase.org/xml/ns/dbchangelog", name = "where", scope = Update.class)
    public JAXBElement<Object> createUpdateWhere(Object value) {
        return new JAXBElement<Object>(_DeleteWhere_QNAME, Object.class, Update.class, value);
    }

}

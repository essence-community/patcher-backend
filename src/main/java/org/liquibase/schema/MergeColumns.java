//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.08.26 at 08:26:28 PM PST 
//


package org.liquibase.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attGroup ref="{http://www.liquibase.org/xml/ns/dbchangelog}tableNameAttribute"/>
 *       &lt;attribute name="column1Name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="joinString" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="column2Name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="finalColumnName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="finalColumnType" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "mergeColumns")
public class MergeColumns {

    @XmlAttribute(name = "column1Name", required = true)
    protected String column1Name;
    @XmlAttribute(name = "joinString", required = true)
    protected String joinString;
    @XmlAttribute(name = "column2Name", required = true)
    protected String column2Name;
    @XmlAttribute(name = "finalColumnName", required = true)
    protected String finalColumnName;
    @XmlAttribute(name = "finalColumnType", required = true)
    protected String finalColumnType;
    @XmlAttribute(name = "catalogName")
    protected String catalogName;
    @XmlAttribute(name = "schemaName")
    protected String schemaName;
    @XmlAttribute(name = "tableName", required = true)
    protected String tableName;

    /**
     * Gets the value of the column1Name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColumn1Name() {
        return column1Name;
    }

    /**
     * Sets the value of the column1Name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColumn1Name(String value) {
        this.column1Name = value;
    }

    /**
     * Gets the value of the joinString property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJoinString() {
        return joinString;
    }

    /**
     * Sets the value of the joinString property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJoinString(String value) {
        this.joinString = value;
    }

    /**
     * Gets the value of the column2Name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColumn2Name() {
        return column2Name;
    }

    /**
     * Sets the value of the column2Name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColumn2Name(String value) {
        this.column2Name = value;
    }

    /**
     * Gets the value of the finalColumnName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFinalColumnName() {
        return finalColumnName;
    }

    /**
     * Sets the value of the finalColumnName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFinalColumnName(String value) {
        this.finalColumnName = value;
    }

    /**
     * Gets the value of the finalColumnType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFinalColumnType() {
        return finalColumnType;
    }

    /**
     * Sets the value of the finalColumnType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFinalColumnType(String value) {
        this.finalColumnType = value;
    }

    /**
     * Gets the value of the catalogName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCatalogName() {
        return catalogName;
    }

    /**
     * Sets the value of the catalogName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCatalogName(String value) {
        this.catalogName = value;
    }

    /**
     * Gets the value of the schemaName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSchemaName() {
        return schemaName;
    }

    /**
     * Sets the value of the schemaName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSchemaName(String value) {
        this.schemaName = value;
    }

    /**
     * Gets the value of the tableName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * Sets the value of the tableName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTableName(String value) {
        this.tableName = value;
    }

}

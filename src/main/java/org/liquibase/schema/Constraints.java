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
 *       &lt;attGroup ref="{http://www.liquibase.org/xml/ns/dbchangelog}constraintsAttributes"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "constraints")
public class Constraints {

    @XmlAttribute(name = "nullable")
    protected String nullable;
    @XmlAttribute(name = "primaryKey")
    protected String primaryKey;
    @XmlAttribute(name = "primaryKeyName")
    protected String primaryKeyName;
    @XmlAttribute(name = "primaryKeyTablespace")
    protected String primaryKeyTablespace;
    @XmlAttribute(name = "unique")
    protected String unique;
    @XmlAttribute(name = "uniqueConstraintName")
    protected String uniqueConstraintName;
    @XmlAttribute(name = "references")
    protected String references;
    @XmlAttribute(name = "referencedTableName")
    protected String referencedTableName;
    @XmlAttribute(name = "referencedColumnNames")
    protected String referencedColumnNames;
    @XmlAttribute(name = "foreignKeyName")
    protected String foreignKeyName;
    @XmlAttribute(name = "deleteCascade")
    protected String deleteCascade;
    @XmlAttribute(name = "deferrable")
    protected String deferrable;
    @XmlAttribute(name = "initiallyDeferred")
    protected String initiallyDeferred;
    @XmlAttribute(name = "checkConstraint")
    protected String checkConstraint;

    /**
     * Gets the value of the nullable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNullable() {
        return nullable;
    }

    /**
     * Sets the value of the nullable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNullable(String value) {
        this.nullable = value;
    }

    /**
     * Gets the value of the primaryKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimaryKey() {
        return primaryKey;
    }

    /**
     * Sets the value of the primaryKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimaryKey(String value) {
        this.primaryKey = value;
    }

    /**
     * Gets the value of the primaryKeyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimaryKeyName() {
        return primaryKeyName;
    }

    /**
     * Sets the value of the primaryKeyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimaryKeyName(String value) {
        this.primaryKeyName = value;
    }

    /**
     * Gets the value of the primaryKeyTablespace property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimaryKeyTablespace() {
        return primaryKeyTablespace;
    }

    /**
     * Sets the value of the primaryKeyTablespace property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimaryKeyTablespace(String value) {
        this.primaryKeyTablespace = value;
    }

    /**
     * Gets the value of the unique property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnique() {
        return unique;
    }

    /**
     * Sets the value of the unique property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnique(String value) {
        this.unique = value;
    }

    /**
     * Gets the value of the uniqueConstraintName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniqueConstraintName() {
        return uniqueConstraintName;
    }

    /**
     * Sets the value of the uniqueConstraintName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniqueConstraintName(String value) {
        this.uniqueConstraintName = value;
    }

    /**
     * Gets the value of the references property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferences() {
        return references;
    }

    /**
     * Sets the value of the references property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferences(String value) {
        this.references = value;
    }

    /**
     * Gets the value of the referencedTableName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferencedTableName() {
        return referencedTableName;
    }

    /**
     * Sets the value of the referencedTableName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferencedTableName(String value) {
        this.referencedTableName = value;
    }

    /**
     * Gets the value of the referencedColumnNames property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferencedColumnNames() {
        return referencedColumnNames;
    }

    /**
     * Sets the value of the referencedColumnNames property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferencedColumnNames(String value) {
        this.referencedColumnNames = value;
    }

    /**
     * Gets the value of the foreignKeyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getForeignKeyName() {
        return foreignKeyName;
    }

    /**
     * Sets the value of the foreignKeyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setForeignKeyName(String value) {
        this.foreignKeyName = value;
    }

    /**
     * Gets the value of the deleteCascade property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeleteCascade() {
        return deleteCascade;
    }

    /**
     * Sets the value of the deleteCascade property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeleteCascade(String value) {
        this.deleteCascade = value;
    }

    /**
     * Gets the value of the deferrable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeferrable() {
        return deferrable;
    }

    /**
     * Sets the value of the deferrable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeferrable(String value) {
        this.deferrable = value;
    }

    /**
     * Gets the value of the initiallyDeferred property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInitiallyDeferred() {
        return initiallyDeferred;
    }

    /**
     * Sets the value of the initiallyDeferred property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInitiallyDeferred(String value) {
        this.initiallyDeferred = value;
    }

    /**
     * Gets the value of the checkConstraint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCheckConstraint() {
        return checkConstraint;
    }

    /**
     * Sets the value of the checkConstraint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCheckConstraint(String value) {
        this.checkConstraint = value;
    }

}

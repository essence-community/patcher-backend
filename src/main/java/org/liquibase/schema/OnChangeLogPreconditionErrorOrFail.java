//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.08.26 at 08:26:28 PM PST 
//


package org.liquibase.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for onChangeLogPreconditionErrorOrFail.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="onChangeLogPreconditionErrorOrFail">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="HALT"/>
 *     &lt;enumeration value="WARN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "onChangeLogPreconditionErrorOrFail")
@XmlEnum
public enum OnChangeLogPreconditionErrorOrFail {

    HALT,
    WARN;

    public String value() {
        return name();
    }

    public static OnChangeLogPreconditionErrorOrFail fromValue(String v) {
        return valueOf(v);
    }

}

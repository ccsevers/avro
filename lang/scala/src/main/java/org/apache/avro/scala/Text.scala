/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.avro.scala

import scala.util.control.Breaks

/**
 * Text utilities.
 */
object Text {
  object Case extends Enumeration {
    val Lower, Upper = Value
  }

  implicit def implicitIndentableFromString(str: String): Indentable = {
    return new Indentable(str)
  }

  implicit def implicitCamelCaseableFromString(str: String): CamelCaseable = {
    return new CamelCaseable(str)
  }

  implicit def implicitFormatableFromString(str: String): Formatable = {
    return new Formatable(str)
  }

  /**
   * Builds a string given a format with place-holders and a dictionary of parameters.
   *
   * This formatting function will be replaced by Scala 2.10 string interpolation.
   *
   * @param format the format string
   * @param params formatting parameters
   * @return the format string with parameters filled in
   */
  def format(format: String, params: Map[Symbol, Any]): String = {
    val sb = new StringBuilder()
    var fmt = format
    while (!fmt.isEmpty()) {
      Breaks.breakable {
        if (fmt.startsWith("%(")) {
          val end = fmt.indexOf(')')
          if (end != -1) {
            val param = fmt.substring(2, end)
            val value = params.get(Symbol(param))
            if (value.isDefined) {
              sb.append(value.get.toString())
              fmt = fmt.substring(end + 1)
              Breaks.break
            }
          }
        }
        sb.append(fmt.charAt(0))
        fmt = fmt.substring(1)
      }
    }
    return sb.toString()
  }

  /** Prepends a prefix to every line in a given text. */
  def prefixLines(text: String, prefix: String): String = {
    return text.split("\n").map(prefix + _).mkString("\n")
  }

  /** Indent a text. */
  def indent(text: String, indent: Int): String = {
    return prefixLines(text, " " * indent)
  }

  /** Convert a string to Camel case. */
  def toCamelCase(identifier: String, textCase: Option[Case.Value] = None): String = {
    val sb = new StringBuffer()
    var index = 0
    while (index < identifier.length()) {
      var char = identifier.charAt(index)
      var upper = false
      while (!char.isLetterOrDigit && (index < identifier.length)) {
        upper = true
        index += 1
        char = identifier.charAt(index)
      }
      if ((sb.length == 0) && textCase.isDefined) {
        sb.append(textCase.get match {
          case Case.Lower => char.toLower
          case Case.Upper => char.toUpper
        })
      } else {
        if (upper) { char = char.toUpper }
        sb.append(char)
      }
      index += 1
    }
    return sb.toString()
  }

}

class Indentable(text: String) {
  def indent(nspaces: Int): String = {
    return Text.indent(text, nspaces)
  }
}

class CamelCaseable(ident: String) {
  def toCamelCase(): String = {
    return Text.toCamelCase(ident, None)
  }

  def toUpperCamelCase(): String = {
    return Text.toCamelCase(ident, Some(Text.Case.Upper))
  }

  def toLowerCamelCase(): String = {
    return Text.toCamelCase(ident, Some(Text.Case.Lower))
  }
}

class Formatable(fmt: String) {
  def xformat(mapping: Tuple2[Symbol, Any]*): String = {
    return Text.format(fmt, Map(mapping: _*))
  }
}

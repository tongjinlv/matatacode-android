/**
 * @license
 * Visual Blocks Editor
 *
 * Copyright 2012 Google Inc.
 * https://developers.google.com/blockly/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @fileoverview Logic blocks for Blockly.
 *
 * This file is scraped to extract a .json file of block definitions. The array
 * passed to defineBlocksWithJsonArray(..) must be strict JSON: double quotes
 * only, no outside references, no functions, no trailing commas, etc. The one
 * exception is end-of-line comments, which the scraper will remove.
 * @author q.neutron@gmail.com (Quynh Neutron)
 */
'use strict';

goog.provide('Blockly.Blocks.logicnew');  // Deprecated
goog.provide('Blockly.Constants.LogicNew');

goog.require('Blockly.Blocks');
goog.require('Blockly');

Blockly.Constants.LogicNew.HUE = 210;

Blockly.defineBlocksWithJsonArray([  // BEGIN JSON EXTRACT
  // Block for boolean data type: true and false.
  {
    "type": "logic_boolean_new",
    "message0": "%1",
    "args0": [
      {
        "type": "field_dropdown",
        "name": "BOOL",
        "options": [
          ["true", "true"],
          ["false", "false"]
        ]
      }
    ],
    "output": "Boolean",
    "colour": Blockly.Constants.LogicNew.HUE,
    "tooltip": "",
    "helpUrl": ""
  },
  // Block for if/elseif/else condition.
  {
    "type": "controls_if_new",
    "message0": "if event %1 == true",
    "args0": [
      {
        "type": "input_value",
        "name": "IF0",
        "check": "Boolean"
      }
    ],
    "message1": "then %1",
    "args1": [
      {
        "type": "input_statement",
        "name": "DO0"
      }
    ],
    "previousStatement": null,
    "nextStatement": null,
    "colour": Blockly.Constants.LogicNew.HUE,
    "helpUrl": "",
  },
  // If/else block that does not use a mutator.
  {
    "type": "controls_ifelse_new",
    "message0": "if %1",
    "args0": [
      {
        "type": "input_value",
        "name": "IF0",
        "check": "Boolean"
      }
    ],
    "message1": "then %1",
    "args1": [
      {
        "type": "input_statement",
        "name": "DO0"
      }
    ],
    "message2": "else %1",
    "args2": [
      {
        "type": "input_statement",
        "name": "ELSE"
      }
    ],
    "previousStatement": null,
    "nextStatement": null,
    "colour": Blockly.Constants.LogicNew.HUE,
    "tooltip": "",
    "helpUrl": ""
  },
  // Block for comparison operator.
  {
    "type": "logic_compare_new",
    "message0": "%1 %2 %3",
    "args0": [
      {
        "type": "input_value",
        "name": "A"
      },
      {
        "type": "field_dropdown",
        "name": "OP",
        "options": [
          ["=", "EQ"],
          ["\u2260", "NEQ"],
          ["\u200F<", "LT"],
          ["\u200F\u2264", "LTE"],
          ["\u200F>", "GT"],
          ["\u200F\u2265", "GTE"]
        ]
      },
      {
        "type": "input_value",
        "name": "B"
      }
    ],
    "inputsInline": true,
    "output": "Boolean",
    "colour": Blockly.Constants.LogicNew.HUE,
    "helpUrl": ""
  },
  {
    "type": "logic_operation_new",
    "message0": "%1 %2 %3",
    "args0": [
      {
        "type": "input_value",
        "name": "A",
        "check": "Boolean"
      },
      {
        "type": "field_dropdown",
        "name": "OP",
        "options": [
          ["&&", "AND"],
          ["||", "OR"]
        ]
      },
      {
        "type": "input_value",
        "name": "B",
        "check": "Boolean"
      }
    ],
    "inputsInline": true,
    "output": "Boolean",
    "colour": Blockly.Constants.LogicNew.HUE,
    "helpUrl": "",
  }
]);  // END JSON EXTRACT (Do not delete this comment.)

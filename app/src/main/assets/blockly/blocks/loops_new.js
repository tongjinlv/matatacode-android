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
 * @fileoverview Loop blocks for Blockly.
 *
 * This file is scraped to extract a .json file of block definitions. The array
 * passed to defineBlocksWithJsonArray(..) must be strict JSON: double quotes
 * only, no outside references, no functions, no trailing commas, etc. The one
 * exception is end-of-line comments, which the scraper will remove.
 * @author fraser@google.com (Neil Fraser)
 */
'use strict';

goog.provide('Blockly.Blocks.loopsnew');  // Deprecated
goog.provide('Blockly.Constants.LoopsNew');

goog.require('Blockly.Blocks');
goog.require('Blockly');


Blockly.Constants.LoopsNew.HUE = 120;

Blockly.defineBlocksWithJsonArray([  // BEGIN JSON EXTRACT
  // Block for repeat n times (external number).
  {
    "type": "loopsnew_count",
    "message0": "%1 Loop %2 times %3 %4",
    "args0": [
      {
        "type": "field_image",
        "src": "media/icons/loop_start.svg",
        "width": 16,
        "height": 16,
        "alt": "*",
        "flipRtl": false
      },
      {
        "type": "field_dropdown",
        "name": "TIMES",
        "options": [
          [
            "1",
            "1"
          ],
          [
            "2",
            "2"
          ],
          [
            "3",
            "3"
          ],
          [
            "4",
            "4"
          ],
          [
            "5",
            "5"
          ],
          [
            "6",
            "6"
          ],
          [
            "7",
            "7"
          ],
          [
            "8",
            "8"
          ],
          [
            "9",
            "9"
          ],
          [
            "10",
            "10"
          ],
          [
            "11",
            "11"
          ],
          [
            "12",
            "12"
          ],
          [
            "13",
            "13"
          ],
          [
            "14",
            "14"
          ],
          [
            "15",
            "15"
          ],
          [
            "16",
            "16"
          ],
          [
            {
              "src": "media/icons/random.svg",
              "width": 16,
              "height": 16,
              "alt": "*"
            },
            "random"
          ]
        ]
      },
      {
        "type": "input_dummy"
      },
      {
        "type": "input_statement",
        "name": "DO1",
        "align": "RIGHT"
      }
    ],
    "previousStatement": null,
    "nextStatement": null,
    "colour": "#388E3C",
    "tooltip": "",
    "helpUrl": ""
  },
  {
    "type": "loopsnew_infi",
    "message0": "%1 Infinitive loop %2 %3",
    "args0": [
      {
        "type": "field_image",
        "src": "media/icons/loop_start.svg",
        "width": 16,
        "height": 16,
        "alt": "*",
        "flipRtl": false
      },
      {
        "type": "input_dummy"
      },
      {
        "type": "input_statement",
        "name": "DO1",
        "align": "RIGHT"
      }
    ],
    "previousStatement": null,
//    "nextStatement": null,
    "colour": "#388E3C",
    "tooltip": "",
    "helpUrl": ""
  },
  {
    "type": "loopsnew_end",
    "message0": "%1 end loop",
    "args0": [
      {
        "type": "field_image",
        "src": "media/icons/loop_break.svg",
        "width": 16,
        "height": 16,
        "alt": "*",
        "flipRtl": false
      }
    ],
    "previousStatement": null,
    "colour": "#388E3C",
    "tooltip": "",
    "helpUrl": ""
  },
  {
    "type": "loopsnew_continue",
    "message0": "%1 loop continue",
    "args0": [
      {
        "type": "field_image",
        "src": "media/icons/loop_break.svg",
        "width": 16,
        "height": 16,
        "alt": "*",
        "flipRtl": false
      }
    ],
    "previousStatement": null,
    "colour": "#388E3C",
    "tooltip": "",
    "helpUrl": ""
  }
]);

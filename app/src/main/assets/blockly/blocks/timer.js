'use strict';

goog.provide('Blockly.Blocks.timer');  // Deprecated
goog.provide('Blockly.Constants.Timer');  // deprecated, 2018 April 5

goog.require('Blockly.Blocks');
goog.require('Blockly');

Blockly.Constants.Timer.HUE = 83;

Blockly.defineBlocksWithJsonArray([{
  "type": "timer_wait",
  "message0": "%2 MatataBot %{BKY_TIMER_WAIT_SEC}",
  "args0": [
    {
      "type": "field_dropdown",
      "name": "SECOND",
      "options": [
        [
          "%{BKY_TIMER_SEC_1}",
          "1"
        ],
        [
          "%{BKY_TIMER_SEC_2}",
          "2"
        ],
        [
          "%{BKY_TIMER_SEC_3}",
          "3"
        ],
        [
          "%{BKY_TIMER_SEC_4}",
          "4"
        ],
        [
          "%{BKY_TIMER_SEC_5}",
          "5"
        ],
        [
          "%{BKY_TIMER_SEC_6}",
          "6"
        ],
        [
          "%{BKY_TIMER_SEC_7}",
          "7"
        ],
        [
          "%{BKY_TIMER_SEC_8}",
          "8"
        ],
        [
          "%{BKY_TIMER_SEC_9}",
          "9"
        ],
        [
          "%{BKY_TIMER_SEC_10}",
          "10"
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
    }, {
      "type": "field_image",
      "src": "media/icons/timer.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    }
  ],
  "previousStatement": null,
  "nextStatement": null,
  "colour": "#F2DE00",
  "tooltip": "",
  "helpUrl": ""
},{
  "type": "timer_wait_ms",
  "message0": "%1 MatataBot 等待 %2 ms",
  "args0": [
    {
      "type": "field_image",
      "src": "media/icons/timer.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    },
    {
      "type": "field_number",
      "name": "MSTIMES",
      "value": 100,
      "min": 0,
      "max": 20000,
      "precision": 100
    }
  ],
  "previousStatement": null,
  "nextStatement": null,
  "colour": "#F2DE00",
  "tooltip": "",
  "helpUrl": ""
}]
); 

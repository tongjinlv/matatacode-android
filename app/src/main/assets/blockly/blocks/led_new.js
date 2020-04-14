'use strict';

goog.provide('Blockly.Blocks.lednew');  // Deprecated
goog.provide('Blockly.Constants.LedNew');  // deprecated, 2018 April 5

goog.require('Blockly.Blocks');
goog.require('Blockly');

Blockly.Constants.LedNew.HUE = 285;

Blockly.defineBlocksWithJsonArray([{
  "type": "sensor_led",
  "message0": "%4 %5 玛塔传感器 %{BKY_LEDNEW_SENSOR}",
  "args0": [
    {
      "type": "field_dropdown",
      "name": "LED",
      "options": [
        [
          "%{BKY_DIR_NEXT}",
          "plus"
        ],
        [
          "%{BKY_DIR_LAST}",
          "minus"
        ],
        [
          "%{BKY_DIR_ALL}",
          "all"
        ]
      ]
    },
    {
      "type": "field_dropdown",
      "name": "COLOR",
      "options": [
        [
          "%{BKY_MATA_COLOR_WHITE}",
          "1"
        ],
        [
          "%{BKY_MATA_COLOR_RED}",
          "2"
        ],
        [
          "%{BKY_MATA_COLOR_YELLOW}",
          "3"
        ],
        [
          "%{BKY_MATA_COLOR_GREEN}",
          "4"
        ],
        [
          "%{BKY_MATA_COLOR_BLUE}",
          "5"
        ],
        [
          "%{BKY_MATA_COLOR_PINK}",
          "6"
        ],
        [
          "%{BKY_MATA_COLOR_BLACK}",
          "7"
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
      "type": "field_dropdown",
      "name": "LEVEL",
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
          {
            "src": "media/icons/random.svg",
            "width": 16,
            "height": 16,
            "alt": "*"
          },
          "random"
        ]
      ]
    },{
      "type": "field_image",
      "src": "media/icons/led_plus.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    },{
      "type": "field_image",
      "src": "media/icons/led_minus.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    }
  ],
  "previousStatement": null,
  "nextStatement": null,
  "colour": "#8222C3",
  "tooltip": "",
  "helpUrl": ""
},
{
  "type": "bot_led",
  "message0": "%4 %5 MatataBot %{BKY_LEDNEW_BOTEYE}",
  "args0": [
    {
      "type": "field_dropdown",
      "name": "LED",
      "options": [
        [
          "%{BKY_DIR_LEFT}",
          "left"
        ],
        [
          "%{BKY_DIR_RIGHT}",
          "right"
        ],
        [
          "%{BKY_DIR_ALL}",
          "all"
        ]
      ]
    },
    {
      "type": "field_dropdown",
      "name": "COLOR",
      "options": [
        [
          "%{BKY_MATA_COLOR_WHITE}",
          "1"
        ],
        [
          "%{BKY_MATA_COLOR_RED}",
          "2"
        ],
        [
          "%{BKY_MATA_COLOR_YELLOW}",
          "3"
        ],
        [
          "%{BKY_MATA_COLOR_GREEN}",
          "4"
        ],
        [
          "%{BKY_MATA_COLOR_BLUE}",
          "5"
        ],
        [
          "%{BKY_MATA_COLOR_PURPLE}",
          "6"
        ],
        [
          "%{BKY_MATA_COLOR_BLACK}",
          "7"
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
      "type": "field_dropdown",
      "name": "LEVEL",
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
          {
            "src": "media/icons/random.svg",
            "width": 16,
            "height": 16,
            "alt": "*"
          },
          "random"
        ]
      ]
    },{
      "type": "field_image",
      "src": "media/icons/right_eye.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    },{
      "type": "field_image",
      "src": "media/icons/left_eye.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    }
  ],
  "previousStatement": null,
  "nextStatement": null,
  "colour": "#8222C3",
  "tooltip": "",
  "helpUrl": ""
},{
  "type": "sensor_ledcyc",
  "message0": "%1 玛塔传感器 灯环 %2 %3 %4 %5 %6 %7 %8 %9 %10 %11 %12 %13 %14 %15 %16 %17 %18 %19 %20 %21 %22 %23",
  "args0": [
    {
      "type": "field_image",
      "src": "media/icons/con.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "field_image",
      "src": "media/null.svg",
      "width": 18,
      "height": 15,
      "alt": "*",
      "flipRtl": false
    },
    {
      "type": "field_colour",
      "name": "12",
      "colour": "#990000"
    },
    {
      "type": "field_colour",
      "name": "1",
      "colour": "#ff0000"
    },
    {
      "type": "field_colour",
      "name": "2",
      "colour": "#ff6666"
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "field_colour",
      "name": "11",
      "colour": "#ffff00"
    },
    {
      "type": "field_image",
      "src": "media/null.svg",
      "width": 94,
      "height": 15,
      "alt": "*",
      "flipRtl": false
    },
    {
      "type": "field_colour",
      "name": "3",
      "colour": "#66ff99"
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "field_colour",
      "name": "10",
      "colour": "#ffcc66"
    },
    {
      "type": "field_image",
      "src": "media/null.svg",
      "width": 94,
      "height": 15,
      "alt": "*",
      "flipRtl": false
    },
    {
      "type": "field_colour",
      "name": "4",
      "colour": "#33cc00"
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "field_colour",
      "name": "9",
      "colour": "#cc9933"
    },
    {
      "type": "field_image",
      "src": "media/null.svg",
      "width": 94,
      "height": 15,
      "alt": "*",
      "flipRtl": false
    },
    {
      "type": "field_colour",
      "name": "5",
      "colour": "#006600"
    },
    {
      "type": "input_dummy"
    },
    {
      "type": "field_image",
      "src": "media/null.svg",
      "width": 18,
      "height": 15,
      "alt": "*",
      "flipRtl": false
    },
    {
      "type": "field_colour",
      "name": "6",
      "colour": "#33ccff"
    },
    {
      "type": "field_colour",
      "name": "7",
      "colour": "#3333ff"
    },
    {
      "type": "field_colour",
      "name": "8",
      "colour": "#000099"
    }
  ],
  "previousStatement": null,
  "nextStatement": null,
  "colour": "#8222C3",
  "tooltip": "",
  "helpUrl": ""
},
{
  "type": "sensor_ledposition",
  "message0": "%1 玛塔传感器 第 %2 个灯设 %3 亮度为 %4",
  "args0": [
    {
      "type": "field_image",
      "src": "media/icons/con.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    },
    {
      "type": "field_dropdown",
      "name": "POSITION",
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
      "type": "field_dropdown",
      "name": "COLOR",
      "options": [
        [
          "%{BKY_MATA_COLOR_WHITE}",
          "1"
        ],
        [
          "%{BKY_MATA_COLOR_RED}",
          "2"
        ],
        [
          "%{BKY_MATA_COLOR_YELLOW}",
          "3"
        ],
        [
          "%{BKY_MATA_COLOR_GREEN}",
          "4"
        ],
        [
          "%{BKY_MATA_COLOR_BLUE}",
          "5"
        ],
        [
          "%{BKY_MATA_COLOR_PINK}",
          "6"
        ],
        [
          "%{BKY_MATA_COLOR_BLACK}",
          "7"
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
      "type": "field_dropdown",
      "name": "BRIGHTNESS",
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
          {
            "src": "media/icons/random.svg",
            "width": 16,
            "height": 16,
            "alt": "*"
          },
          "random"
        ]
      ]
    }
  ],
  "previousStatement": null,
  "nextStatement": null,
  "colour": "#8222C3",
  "tooltip": "",
  "helpUrl": ""
},{
  "type": "sensor_ledallrgb",
  "message0": "%1 玛塔控制器 灯环亮 R: %2  G: %3   B: %4",
  "args0": [
    {
      "type": "field_image",
      "src": "media/icons/con.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    },
    {
      "type": "field_number",
      "name": "R",
      "value": 0,
      "min": 0,
      "max": 100,
      "precision": 1
    },
    {
      "type": "field_number",
      "name": "G",
      "value": 0,
      "min": 0,
      "max": 100,
      "precision": 1
    },
    {
      "type": "field_number",
      "name": "B",
      "value": 0,
      "min": 0,
      "max": 100,
      "precision": 1
    }
  ],
  "inputsInline": true,
  "previousStatement": null,
  "nextStatement": null,
  "colour": "#8222C3",
  "tooltip": "",
  "helpUrl": ""
},{
  "type": "sensor_ledall",
  "message0": "%1 玛塔控制器 灯环亮 %2 色，亮度 %3",
  "args0": [
    {
      "type": "field_image",
      "src": "media/icons/con.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    },
    {
      "type": "field_dropdown",
      "name": "COLOR",
      "options": [
        [
          "%{BKY_MATA_COLOR_WHITE}",
          "1"
        ],
        [
          "%{BKY_MATA_COLOR_RED}",
          "2"
        ],
        [
          "%{BKY_MATA_COLOR_YELLOW}",
          "3"
        ],
        [
          "%{BKY_MATA_COLOR_GREEN}",
          "4"
        ],
        [
          "%{BKY_MATA_COLOR_BLUE}",
          "5"
        ],
        [
          "%{BKY_MATA_COLOR_PURPLE}",
          "6"
        ],
        [
          "%{BKY_MATA_COLOR_BLACK}",
          "7"
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
      "type": "field_dropdown",
      "name": "LEVEL",
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
          {
            "src": "media/icons/random.svg",
            "width": 16,
            "height": 16,
            "alt": "*"
          },
          "random"
        ]
      ]
    }
  ],
  "inputsInline": true,
  "previousStatement": null,
  "nextStatement": null,
  "colour": "#8222C3",
  "tooltip": "",
  "helpUrl": ""
},{
  "type": "sensor_ledrgb",
  "message0": "%1 %2 玛塔控制器 设置 %3 灯为 R: %4 G: %5 B: %6",
  "args0": [
    {
      "type": "field_image",
      "src": "media/icons/led_plus.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    },
    {
      "type": "field_image",
      "src": "media/icons/led_minus.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    },
    {
      "type": "field_dropdown",
      "name": "LED",
      "options": [
        [
          "%{BKY_DIR_NEXT}",
          "plus"
        ],
        [
          "%{BKY_DIR_LAST}",
          "minus"
        ],
        [
          "%{BKY_DIR_ALL}",
          "all"
        ]
      ]
    },
    {
      "type": "field_number",
      "name": "R",
      "value": 0,
      "min": 0,
      "max": 100,
      "precision": 1
    },
    {
      "type": "field_number",
      "name": "G",
      "value": 0,
      "min": 0,
      "max": 100,
      "precision": 1
    },
    {
      "type": "field_number",
      "name": "B",
      "value": 0,
      "min": 0,
      "max": 100,
      "precision": 1
    }
  ],
  "inputsInline": true,
  "previousStatement": null,
  "nextStatement": null,
  "colour": "#8222C3",
  "tooltip": "",
  "helpUrl": ""
},{
  "type": "sensor_ledpositionrgb",
  "message0": "%1 玛塔控制器 第 %2 个灯为 R: %3 G: %4 B: %5",
  "args0": [
    {
      "type": "field_image",
      "src": "media/icons/con.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    },
    {
      "type": "field_dropdown",
      "name": "POSITION",
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
      "type": "field_number",
      "name": "R",
      "value": 0,
      "min": 0,
      "max": 100,
      "precision": 1
    },
    {
      "type": "field_number",
      "name": "G",
      "value": 0,
      "min": 0,
      "max": 100,
      "precision": 1
    },
    {
      "type": "field_number",
      "name": "B",
      "value": 0,
      "min": 0,
      "max": 100,
      "precision": 1
    }
  ],
  "inputsInline": true,
  "previousStatement": null,
  "nextStatement": null,
  "colour": "#8222C3",
  "tooltip": "",
  "helpUrl": ""
},{
  "type": "bot_eyergb",
  "message0": "%1 %6 MatataBot 设置 %2 眼为 R: %3 G: %4 B: %5",
  "args0": [
    {
      "type": "field_image",
      "src": "media/icons/right_eye.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    },
    {
      "type": "field_dropdown",
      "name": "LED",
      "options": [
        [
          "%{BKY_DIR_LEFT}",
          "left"
        ],
        [
          "%{BKY_DIR_RIGHT}",
          "right"
        ],
        [
          "%{BKY_DIR_ALL}",
          "all"
        ]
      ]
    },
    {
      "type": "field_number",
      "name": "R",
      "value": 0,
      "min": 0,
      "max": 100,
      "precision": 1
    },
    {
      "type": "field_number",
      "name": "G",
      "value": 0,
      "min": 0,
      "max": 100,
      "precision": 1
    },
    {
      "type": "field_number",
      "name": "B",
      "value": 0,
      "min": 0,
      "max": 100,
      "precision": 1
    },
    {
      "type": "field_image",
      "src": "media/icons/left_eye.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    }
  ],
  "inputsInline": true,
  "previousStatement": null,
  "nextStatement": null,
  "colour": "#8222C3",
  "tooltip": "",
  "helpUrl": ""
},{
  "type": "sensor_ledanimation",
  "message0": "%1 玛塔控制器 灯环播放 %2 直到结束",
  "args0": [
    {
      "type": "field_image",
      "src": "media/icons/con.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    },
    {
      "type": "field_dropdown",
      "name": "ANIMATION",
      "options": [
        [
          "跑马灯",
          "1"
        ],
        [
          "彩虹灯",
          "2"
        ],
        [
          "呼吸灯",
          "3"
        ]
      ]
    }
  ],
  "inputsInline": true,
  "previousStatement": null,
  "nextStatement": null,
  "colour": "#8222C3",
  "tooltip": "",
  "helpUrl": ""
}]
);

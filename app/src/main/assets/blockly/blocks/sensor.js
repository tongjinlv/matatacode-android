'use strict';

goog.provide('Blockly.Blocks.sensor');  // Deprecated
goog.provide('Blockly.Constants.Sensor');  // deprecated, 2018 April 5

goog.require('Blockly.Blocks');
goog.require('Blockly');

Blockly.Constants.Sensor.HUE = 181;

Blockly.defineBlocksWithJsonArray([{
  "type": "sensor",
  "message0": "%2  玛塔传感器 %{BKY_SENSOR_WAIT_EVENT}",
  "args0": [
    {
      "type": "field_dropdown",
      "name": "CONDITION",
      "options": [
        [
          "%{BKY_SENSOR_EVENT_1}",
          "1"
        ],
        [
          "%{BKY_SENSOR_EVENT_2}",
          "2"
        ],
        [
          "%{BKY_SENSOR_EVENT_3}",
          "3"
        ],
        [
          "%{BKY_SENSOR_EVENT_4}",
          "4"
        ],
        [
          "%{BKY_SENSOR_EVENT_5}",
          "5"
        ],
        [
          "%{BKY_SENSOR_EVENT_6}",
          "6"
        ],
        [
          "%{BKY_SENSOR_EVENT_7}",
          "7"
        ],
        [
          "%{BKY_SENSOR_EVENT_8}",
          "8"
        ],
        [
          "%{BKY_SENSOR_EVENT_9}",
          "9"
        ],
        [
          "%{BKY_SENSOR_EVENT_10}",
          "10"
        ]
      ]
    },{
      "type": "field_image",
      "src": "media/icons/sensor.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    }
  ],
  "previousStatement": null,
  "nextStatement": null,
  "colour": "#89D9E4",//Blockly.Constants.Sensor.HUE,
  "tooltip": "",
  "helpUrl": ""
},
{
  "type": "sensor_send_data",
  "message0": "%2  玛塔传感器 %{BKY_SENSOR_SEND_DATA}",
  "args0": [
    {
      "type": "field_dropdown",
      "name": "NUMBER",
      "options": [
        [
          "%{BKY_SENSOR_DATA_1}",
          "1"
        ],
        [
          "%{BKY_SENSOR_DATA_2}",
          "2"
        ],
        [
          "%{BKY_SENSOR_DATA_3}",
          "3"
        ],
        [
          "%{BKY_SENSOR_DATA_4}",
          "4"
        ],
        [
          "%{BKY_SENSOR_DATA_5}",
          "5"
        ],
        [
          "%{BKY_SENSOR_DATA_6}",
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
      "src": "media/icons/send.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    }
  ],
  "previousStatement": null,
  "nextStatement": null,
  "colour": "#89D9E4",
  "tooltip": "",
  "helpUrl": ""
},
{
  "type": "sensor_wait_data",
  "message0": "%2  玛塔传感器 %{BKY_SENSOR_WAIT_DATA}",
  "args0": [
    {
      "type": "field_dropdown",
      "name": "NUMBER",
      "options": [
        [
          "%{BKY_SENSOR_DATA_1}",
          "1"
        ],
        [
          "%{BKY_SENSOR_DATA_2}",
          "2"
        ],
        [
          "%{BKY_SENSOR_DATA_3}",
          "3"
        ],
        [
          "%{BKY_SENSOR_DATA_4}",
          "4"
        ],
        [
          "%{BKY_SENSOR_DATA_5}",
          "5"
        ],
        [
          "%{BKY_SENSOR_DATA_6}",
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
      "src": "media/icons/recive.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    }
  ],
  "previousStatement": null,
  "nextStatement": null,
  "colour": "#89D9E4",
  "tooltip": "",
  "helpUrl": ""
},{
  "type": "get_channel_rgb",
  "message0": "%1 玛塔传感器 %2 色通道值",
  "args0": [
    {
      "type": "field_image",
      "src": "media/icons/rgb.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    },
    {
      "type": "field_dropdown",
      "name": "CHANNEL",
      "options": [
        [
          "红",
          "R"
        ],
        [
          "绿",
          "G"
        ],
        [
          "蓝",
          "B"
        ]
      ]
    }
  ],
  "output": "Number",
  "colour": "#3B7FF8",
  "tooltip": "",
  "helpUrl": ""
},
{
  "type": "get_axis",
  "message0": "%1 玛塔传感器 %2 轴加速度",
  "args0": [
    {
      "type": "field_image",
      "src": "media/icons/axis.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    },
    {
      "type": "field_dropdown",
      "name": "AXIS",
      "options": [
        [
          "X",
          "x"
        ],
        [
          "Y",
          "y"
        ],
        [
          "Z",
          "z"
        ]
      ]
    }
  ],
  "output": "Number",
  "colour": "#3B7FF8",
  "tooltip": "",
  "helpUrl": ""
},
{
  "type": "accelerate_check",
  "message0": "%1 玛塔传感器 发生 %2",
  "args0": [
    {
      "type": "field_image",
      "src": "media/icons/accelerate.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    },
    {
      "type": "field_dropdown",
      "name": "CONDITION",
      "options": [
        [
          "摇晃",
          "1"
        ],
        [
          "平静",
          "2"
        ],
        [
          "倾斜",
          "3"
        ],
        [
          "跌落",
          "4"
        ],
        [
          "正面朝上",
          "5"
        ],
        [
          "背面朝上",
          "6"
        ]
      ]
    }
  ],
  "inputsInline": true,
  "output": "Boolean",
  "colour": "#89D9E4",
  "tooltip": "",
  "helpUrl": ""
},
{
  "type": "rgb_check",
  "message0": "%1 玛塔传感器 识别到颜色 %2",
  "args0": [
    {
      "type": "field_image",
      "src": "media/icons/color.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    },
    {
      "type": "field_dropdown",
      "name": "CONDITION",
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
            "src": "media/random.svg",
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
  "output": "Boolean",
  "colour": "#89D9E4",
  "tooltip": "",
  "helpUrl": ""
},
{
  "type": "btn_check",
  "message0": "%1 玛塔传感器  %2 键被按下",
  "args0": [
    {
      "type": "field_image",
      "src": "media/icons/btn.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    },
    {
      "type": "field_dropdown",
      "name": "CONDITION",
      "options": [
        [
          "前进",
          "1"
        ],
        [
          "后退",
          "2"
        ],
        [
          "左转",
          "3"
        ],
        [
          "右转",
          "4"
        ],
        [
          "执行",
          "5"
        ],
        [
          "停止",
          "6"
        ],
        [
          "音乐",
          "7"
        ]
      ]
    }
  ],
  "inputsInline": true,
  "output": "Boolean",
  "colour": "#89D9E4",
  "tooltip": "",
  "helpUrl": ""
},
{
  "type": "sound_check",
  "message0": "%1 玛塔传感器 听到声音",
  "args0": [
    {
      "type": "field_image",
      "src": "media/icons/sound.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    }
  ],
  "inputsInline": true,
  "output": "Boolean",
  "colour": "#89D9E4",
  "tooltip": "",
  "helpUrl": ""
},
{
  "type": "bump_check",
  "message0": "%1 玛塔传感器 检测到障碍",
  "args0": [
    {
      "type": "field_image",
      "src": "media/icons/bump.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    }
  ],
  "inputsInline": true,
  "output": "Boolean",
  "colour": "#89D9E4",
  "tooltip": "",
  "helpUrl": ""
},{
  "type": "data_check",
  "message0": "%1 玛塔传感器 接收到信息 %2",
  "args0": [
    {
      "type": "field_image",
      "src": "media/icons/recive.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    },
    {
      "type": "field_dropdown",
      "name": "CONDITION",
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
        ]
      ]
    }
  ],
  "inputsInline": true,
  "output": "Boolean",
  "colour": "#89D9E4",
  "tooltip": "",
  "helpUrl": ""
},{
  "type": "get_brightness",
  "message0": "%1 玛塔传感器 明暗度",
  "args0": [
    {
      "type": "field_image",
      "src": "media/icons/brightness.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    }
  ],
  "output": "Number",
  "colour": "#3B7FF8",
  "tooltip": "",
  "helpUrl": ""
},
{
  "type": "brightness_check",
  "message0": "%1 玛塔传感器 识别到 %2",
  "args0": [
    {
      "type": "field_image",
      "src": "media/icons/bright.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    },
    {
      "type": "field_dropdown",
      "name": "CONDITION",
      "options": [
        [
          "亮",
          "1"
        ],
        [
          "暗",
          "2"
        ]
      ]
    }
  ],
  "inputsInline": true,
  "output": "Boolean",
  "colour": "#89D9E4",
  "tooltip": "",
  "helpUrl": ""
},{
  "type": "sensor_waituntil",
  "message0": "%1 等待 %2",
  "args0": [
    {
      "type": "field_image",
      "src": "media/icons/sensor.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    },
    {
      "type": "input_value",
      "name": "UNTIL",
      "check": "Boolean"
    }
  ],
  "previousStatement": null,
  "nextStatement": null,
  "colour": "#89D9E4",
  "tooltip": "",
  "helpUrl": ""
},{
  "type": "clear_check",
  "message0": "%1 玛塔传感器 检测到障碍消失",
  "args0": [
    {
      "type": "field_image",
      "src": "media/icons/unbump.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    }
  ],
  "inputsInline": true,
  "output": "Boolean",
  "colour": "#89D9E4",
  "tooltip": "",
  "helpUrl": ""
},{
  "type": "get_angle",
  "message0": "%1 玛塔传感器 %2",
  "args0": [
    {
      "type": "field_image",
      "src": "media/icons/angle.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    },
    {
      "type": "field_dropdown",
      "name": "ANGLE",
      "options": [
        [
          "仰角",
          "1"
        ],
        [
          "俯角",
          "2"
        ],
        [
          "左倾角",
          "3"
        ],
        [
          "右倾角",
          "4"
        ]
      ]
    }
  ],
  "output": "Number",
  "colour": "#3B7FF8",
  "tooltip": "",
  "helpUrl": ""
}]
); 

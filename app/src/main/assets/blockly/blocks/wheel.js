'use strict';

goog.provide('Blockly.Blocks.wheel');  // Deprecated
goog.provide('Blockly.Constants.Wheel');  // deprecated, 2018 April 5

goog.require('Blockly.Blocks');
goog.require('Blockly');

Blockly.Constants.Wheel.HUE = 355;

Blockly.defineBlocksWithJsonArray([{
  "type": "wheel_control",
  "message0": "%1 Wheels %2 with speed level %3",
  "args0": [
    {
      "type": "field_dropdown",
      "name": "WHEEL",
      "options": [
        [
          "Left",
          "left"
        ],
        [
          "Right",
          "right"
        ]
      ]
    },
    {
      "type": "field_dropdown",
      "name": "DIRECTION",
      "options": [
        [
          "Forward",
          "forward"
        ],
        [
          "Backward",
          "backward"
        ]
      ]
    },
    {
      "type": "field_dropdown",
      "name": "NAME",
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
  "previousStatement": null,
  "nextStatement": null,
  "colour": "#C94637",
  "tooltip": "",
  "helpUrl": ""
},
{
  "type": "both_wheel_control",
  "message0": "Both wheels %1 with speed %2",
  "args0": [
    {
      "type": "field_dropdown",
      "name": "DIRECTION",
      "options": [
        [
          "forward",
          "forward"
        ],
        [
          "backward",
          "backward"
        ]
      ]
    },
    {
      "type": "field_dropdown",
      "name": "SPEED",
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
  "previousStatement": null,
  "nextStatement": null,
  "colour": "#C94637",
  "tooltip": "",
  "helpUrl": ""
},
{
  "type": "wheel_control_new",
  "message0": "%1 %{BKY_WHEEL_CONCTRL} %4",
  "args0": [{
    "type": "field_image",
    "src": "media/icons/left_wheel.svg",
    "width": 18,
    "height": 18,
    "alt": "*",
    "flipRtl": false
  },
  {
    "type": "field_dropdown",
    "name": "lspeed",
    "options": [
      [
        "%{BKY_WHEEL_NULL}",
        "15"
      ],
      [
        "%{BKY_WHEEL_M6}",
        "0"
      ],
      [
        "%{BKY_WHEEL_M5}",
        "1"
      ],
      [
        "%{BKY_WHEEL_M4}",
        "2"
      ],
      [
        "%{BKY_WHEEL_M3}",
        "3"
      ],
      [
        "%{BKY_WHEEL_M2}",
        "4"
      ],
      [
        "%{BKY_WHEEL_M1}",
        "5"
      ],
      [
        "%{BKY_WHEEL_STOP}",
        "6"
      ],
      [
        "%{BKY_WHEEL_P1}",
        "7"
      ],
      [
        "%{BKY_WHEEL_P2}",
        "8"
      ],
      [
        "%{BKY_WHEEL_P3}",
        "9"
      ],
      [
        "%{BKY_WHEEL_P4}",
        "10"
      ],
      [
        "%{BKY_WHEEL_P5}",
        "11"
      ],
      [
        "%{BKY_WHEEL_P6}",
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
    "name": "rspeed",
    "options": [
      [
        "%{BKY_WHEEL_NULL}",
        "15"
      ],
      [
        "%{BKY_WHEEL_M6}",
        "0"
      ],
      [
        "%{BKY_WHEEL_M5}",
        "1"
      ],
      [
        "%{BKY_WHEEL_M4}",
        "2"
      ],
      [
        "%{BKY_WHEEL_M3}",
        "3"
      ],
      [
        "%{BKY_WHEEL_M2}",
        "4"
      ],
      [
        "%{BKY_WHEEL_M1}",
        "5"
      ],
      [
        "%{BKY_WHEEL_STOP}",
        "6"
      ],
      [
        "%{BKY_WHEEL_P1}",
        "7"
      ],
      [
        "%{BKY_WHEEL_P2}",
        "8"
      ],
      [
        "%{BKY_WHEEL_P3}",
        "9"
      ],
      [
        "%{BKY_WHEEL_P4}",
        "10"
      ],
      [
        "%{BKY_WHEEL_P5}",
        "11"
      ],
      [
        "%{BKY_WHEEL_P6}",
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
  }, {
    "type": "field_image",
    "src": "media/icons/right_wheel.svg",
    "width": 18,
    "height": 18,
    "alt": "*",
    "flipRtl": false
  }
  ],
  "previousStatement": null,
  "nextStatement": null,
  "colour": "#C94637",
  "tooltip": "",
  "helpUrl": ""
},
{
  "type": "wheel_stop",
  "message0": "Stop %1 wheel",
  "args0": [
    {
      "type": "field_dropdown",
      "name": "WHEEL",
      "options": [
        [
          "left",
          "left"
        ],
        [
          "right",
          "right"
        ],
        [
          "both",
          "both"
        ]
      ]
    }
  ],
  "previousStatement": null,
  "nextStatement": null,
  "colour": Blockly.Constants.Wheel.HUE,
  "tooltip": "",
  "helpUrl": ""
},
{
  "type": "wheel_control_new_left",
  "message0": "%1 %{BKY_WHEEL_CONCTRL_LEFT}",
  "args0": [{
    "type": "field_image",
    "src": "media/icons/left_wheel.svg",
    "width": 18,
    "height": 18,
    "alt": "*",
    "flipRtl": false
  },
  {
    "type": "field_dropdown",
    "name": "lspeed",
    "options": [
      [
        "%{BKY_WHEEL_M6}",
        "0"
      ],
      [
        "%{BKY_WHEEL_M5}",
        "1"
      ],
      [
        "%{BKY_WHEEL_M4}",
        "2"
      ],
      [
        "%{BKY_WHEEL_M3}",
        "3"
      ],
      [
        "%{BKY_WHEEL_M2}",
        "4"
      ],
      [
        "%{BKY_WHEEL_M1}",
        "5"
      ],
      [
        "%{BKY_WHEEL_STOP}",
        "6"
      ],
      [
        "%{BKY_WHEEL_P1}",
        "7"
      ],
      [
        "%{BKY_WHEEL_P2}",
        "8"
      ],
      [
        "%{BKY_WHEEL_P3}",
        "9"
      ],
      [
        "%{BKY_WHEEL_P4}",
        "10"
      ],
      [
        "%{BKY_WHEEL_P5}",
        "11"
      ],
      [
        "%{BKY_WHEEL_P6}",
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
  }
  ],
  "previousStatement": null,
  "nextStatement": null,
  "colour": "#C94637",
  "tooltip": "",
  "helpUrl": ""
},
{
  "type": "wheel_control_new_right",
  "message0": "%1 %{BKY_WHEEL_CONCTRL_RIGHT}",
  "args0": [{
    "type": "field_image",
    "src": "media/icons/right_wheel.svg",
    "width": 18,
    "height": 18,
    "alt": "*",
    "flipRtl": false
  },
  {
    "type": "field_dropdown",
    "name": "rspeed",
    "options": [
      [
        "%{BKY_WHEEL_M6}",
        "0"
      ],
      [
        "%{BKY_WHEEL_M5}",
        "1"
      ],
      [
        "%{BKY_WHEEL_M4}",
        "2"
      ],
      [
        "%{BKY_WHEEL_M3}",
        "3"
      ],
      [
        "%{BKY_WHEEL_M2}",
        "4"
      ],
      [
        "%{BKY_WHEEL_M1}",
        "5"
      ],
      [
        "%{BKY_WHEEL_STOP}",
        "6"
      ],
      [
        "%{BKY_WHEEL_P1}",
        "7"
      ],
      [
        "%{BKY_WHEEL_P2}",
        "8"
      ],
      [
        "%{BKY_WHEEL_P3}",
        "9"
      ],
      [
        "%{BKY_WHEEL_P4}",
        "10"
      ],
      [
        "%{BKY_WHEEL_P5}",
        "11"
      ],
      [
        "%{BKY_WHEEL_P6}",
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
  }
  ],
  "previousStatement": null,
  "nextStatement": null,
  "colour": "#C94637",
  "tooltip": "",
  "helpUrl": ""
},{
  "type": "wheel_speedall",
  "message0": "%1 %10 MatataBot %2 %3 左轮 %4 速度 %5 %6 %7 右轮 %8 速度 %9",
  "args0": [
    {
      "type": "field_image",
      "src": "media/icons/bot_run1.svg",
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
      "src": "media/icons/left_wheel.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    },
    {
      "type": "field_dropdown",
      "name": "ldir",
      "options": [
        [
          "向前",
          "1"
        ],
        [
          "向后",
          "2"
        ]
      ]
    },
    {
      "type": "field_dropdown",
      "name": "lspeed",
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
          "停止",
          "0"
        ],
        [
          "不变",
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
      "type": "input_dummy"
    },
    {
      "type": "field_image",
      "src": "media/icons/right_wheel.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    },
    {
      "type": "field_dropdown",
      "name": "rdir",
      "options": [
        [
          "向前",
          "1"
        ],
        [
          "向后",
          "2"
        ]
      ]
    },
    {
      "type": "field_dropdown",
      "name": "rspeed",
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
          "停止",
          "0"
        ],
        [
          "不变",
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
    }, {
      "type": "field_image",
      "src": "media/icons/bot_run2.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    }
  ],
  "previousStatement": null,
  "nextStatement": null,
  "colour": "#C94637",
  "tooltip": "",
  "helpUrl": ""
},{
  "type": "wheel_stop",
  "message0": "%1 MatataBot %2 停止运动",
  "args0": [
    {
      "type": "field_image",
      "src": "media/icons/wheels.svg",
      "width": 18,
      "height": 18,
      "alt": "*",
      "flipRtl": false
    },
    {
      "type": "field_dropdown",
      "name": "WHICH",
      "options": [
        [
          "两个轮子",
          "1"
        ],
        [
          "右轮",
          "2"
        ],
        [
          "左轮",
          "3"
        ]
      ]
    }
  ],
  "previousStatement": null,
  "nextStatement": null,
  "colour": "#C94637",
  "tooltip": "",
  "helpUrl": ""
}]);

'use strict';

goog.provide('Blockly.JavaScript.led');

goog.require('Blockly.JavaScript');


// If any new block imports any library, add that library name here.
Blockly.JavaScript.addReservedWords('led');


Blockly.JavaScript['led_left'] = function(block) {
    var colour_color = block.getFieldValue('LED');
    // TODO: Assemble JavaScript into code variable.
    var code = '{\"block\":\"eyeshowscolor\",\"color\":\"' + colour_color + '\",\"which\":\"left\",\"level\":\"1\"}';
    return code;
  };

Blockly.JavaScript['led_right'] = function(block) {
    var colour_color = block.getFieldValue('LED');
    // TODO: Assemble JavaScript into code variable.
    var code = '{\"block\":\"eyeshowscolor\",\"color\":\"' + colour_color + '\",\"which\":\"right\",\"level\":\"1\"}';
    return code;
};

Blockly.JavaScript['led_both'] = function(block) {
    var colour_color = block.getFieldValue('LED');
    // TODO: Assemble JavaScript into code variable.
    var code = '{\"block\":\"eyeshowscolor\",\"color\":\"' + colour_color + '\",\"which\":\"both\",\"level\":\"1\"}';
    return code;
};
  
  Blockly.JavaScript['led_led'] = function(block) {
    var dropdown_eye = block.getFieldValue('EYE');
    var colour_color = block.getFieldValue('COLOR');
    // TODO: Assemble JavaScript into code variable.
    var code = '{\"block\":\"eyeshowscolor\",\"color\":\"' + colour_color + '\",\"which\":\"' + dropdown_eye + '\",\"level\":\"1\"}';
    return code;
  };
Blockly.JavaScript['led_position_color_level'] = function(block) {
    var dropdown_eye = block.getFieldValue('EYE');
    var dropdown_color = block.getFieldValue('COLOR');
    var dropdown_level = block.getFieldValue('LEVEL');
    // TODO: Assemble JavaScript into code variable.
     var code = '{\"block\":\"eyeshowscolor\",\"color\":\"' + colour_color + '\",\"which\":\"' + dropdown_eye + '\",\"level\":\"' + dropdown_level + '\"}';
     return code;
};

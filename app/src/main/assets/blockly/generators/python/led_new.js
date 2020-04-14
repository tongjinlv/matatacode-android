'use strict';

goog.provide('Blockly.Python.lednew');

goog.require('Blockly.Python');


// If any new block imports any library, add that library name here.
Blockly.Python.addReservedWords('lednew');

Blockly.Python['sensor_led'] = function(block) {
  var dropdown_led = block.getFieldValue('LED');
  var dropdown_color = block.getFieldValue('COLOR');
  var dropdown_level = block.getFieldValue('LEVEL');
  // TODO: Assemble Python into code variable.
  var code = '...\n';
  return code;
};

Blockly.Python['bot_led'] = function(block) {
  var dropdown_led = block.getFieldValue('LED');
  var dropdown_color = block.getFieldValue('COLOR');
  var dropdown_level = block.getFieldValue('LEVEL');
  // TODO: Assemble Python into code variable.
  var code = '...\n';
  return code;
};
Blockly.Python['sensor_ledcyc'] = function(block) {
  var colour_12 = block.getFieldValue('12');
  var colour_1 = block.getFieldValue('1');
  var colour_2 = block.getFieldValue('2');
  var colour_11 = block.getFieldValue('11');
  var colour_3 = block.getFieldValue('3');
  var colour_10 = block.getFieldValue('10');
  var colour_4 = block.getFieldValue('4');
  var colour_9 = block.getFieldValue('9');
  var colour_5 = block.getFieldValue('5');
  var colour_6 = block.getFieldValue('6');
  var colour_7 = block.getFieldValue('7');
  var colour_8 = block.getFieldValue('8');
  // TODO: Assemble Python into code variable.
  var code = 'matatacon.halo.set_all(\"'+ colour_1 +'\",\"'+ colour_2 +'\",\"'+ colour_3 +'\",\"'+ colour_4 +'\",\"'+ colour_5 +'\",\"'+ colour_6 +'\",\"'+ colour_7 +'\",\"'+ colour_8 +'\",\"'+ colour_9 +'\",\"'+ colour_10 +'\",\"'+ colour_11 +'\",\"'+ colour_12 +'\")\n';
  return code;
};

Blockly.Python['sensor_ledposition'] = function(block) {
  var dropdown_1 = block.getFieldValue('POSITION');
  var colour_1 = block.getFieldValue('COLOR');
  var dropdown_2 = block.getFieldValue('BRIGHTNESS');
  // TODO: Assemble Python into code variable.
  var code = '...;\n';
  return code;
};


Blockly.Python['sensor_ledallrgb'] = function(block) {
  var number_r = block.getFieldValue('R');
  var number_g = block.getFieldValue('G');
  var number_b = block.getFieldValue('B');
  // TODO: Assemble Python into code variable.
  var code = '...\n';
  return code;
};

Blockly.Python['sensor_ledall'] = function(block) {
  var dropdown_color = block.getFieldValue('COLOR');
  var dropdown_level = block.getFieldValue('LEVEL');
  // TODO: Assemble Python into code variable.
  var code = '...\n';
  return code;
};
Blockly.Python['sensor_ledrgb'] = function(block) {
  var dropdown_led = block.getFieldValue('LED');
  var number_r = block.getFieldValue('R');
  var number_g = block.getFieldValue('G');
  var number_b = block.getFieldValue('B');
  // TODO: Assemble Python into code variable.
  var code = '...;\n';
  return code;
};
Blockly.Python['sensor_ledpositionrgb'] = function(block) {
  var dropdown_position = block.getFieldValue('POSITION');
  var number_r = block.getFieldValue('R');
  var number_g = block.getFieldValue('G');
  var number_b = block.getFieldValue('B');
  // TODO: Assemble Python into code variable.
  var code = '...;\n';
  return code;
};
Blockly.Python['bot_eyergb'] = function(block) {
  var dropdown_led = block.getFieldValue('LED');
  var number_r = block.getFieldValue('R');
  var number_g = block.getFieldValue('G');
  var number_b = block.getFieldValue('B');
  // TODO: Assemble Python into code variable.
  var code = '...;\n';
  return code;
};
Blockly.Python['sensor_ledanimation'] = function(block) {
  var dropdown_animation = block.getFieldValue('ANIMATION');
  // TODO: Assemble Python into code variable.
  var code = '...;\n';
  return code;
};
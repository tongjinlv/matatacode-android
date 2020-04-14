'use strict';

goog.provide('Blockly.Python.wheel');

goog.require('Blockly.Python');


// If any new block imports any library, add that library name here.
Blockly.Python.addReservedWords('wheel');

Blockly.Python['wheel_control'] = function(block) {
  var dropdown_wheel = block.getFieldValue('WHEEL');
  var dropdown_direction = block.getFieldValue('DIRECTION');
  var dropdown_name = block.getFieldValue('NAME');
  // TODO: Assemble Python into code variable.
  var code = '...\n';
  return code;
};

Blockly.Python['both_wheel_control'] = function(block) {
  var dropdown_direction = block.getFieldValue('DIRECTION');
  var dropdown_speed = block.getFieldValue('SPEED');
  // TODO: Assemble Python into code variable.
  var code = '...\n';
  return code;
};

Blockly.Python['wheel_stop'] = function(block) {
  var dropdown_wheel = block.getFieldValue('WHEEL');
  // TODO: Assemble Python into code variable.
  var code = '...\n';
  return code;
};

Blockly.Python['wheel_control_new'] = function(block) {
  var dropdown_lspeed = block.getFieldValue('lspeed');
  var dropdown_rspeed = block.getFieldValue('rspeed');
  // TODO: Assemble Python into code variable.
  var code = '...\n';
  return code;
};
Blockly.Python['wheel_control_new_left'] = function(block) {
  var dropdown_lspeed = block.getFieldValue('lspeed');
  // TODO: Assemble Python into code variable.
  var code = '...\n';
  return code;
};
Blockly.Python['wheel_control_new_right'] = function(block) {
  var dropdown_rspeed = block.getFieldValue('rspeed');
  // TODO: Assemble Python into code variable.
  var code = '...\n';
  return code;
};

Blockly.Python['wheel_speed'] = function(block) {
  var dropdown_1 = block.getFieldValue('WHICH');
  var dropdown_2 = block.getFieldValue('DIR');
  var dropdown_3 = block.getFieldValue('SPEED');
  // TODO: Assemble Python into code variable.
  var code = '...;\n';
  return code;
};
Blockly.Python['wheel_stop'] = function(block) {
  var dropdown_name = block.getFieldValue('WHICH');
  // TODO: Assemble Python into code variable.
  var code = '...;\n';
  return code;
};
Blockly.Python['wheel_speedall'] = function(block) {
  var dropdown_ldir = block.getFieldValue('LDIR');
  var dropdown_lspeed = block.getFieldValue('LSPEED');
  var dropdown_rdir = block.getFieldValue('RDIR');
  var dropdown_rspeed = block.getFieldValue('RSPEED');
  // TODO: Assemble Python into code variable.
  var code = '...;\n';
  return code;
};
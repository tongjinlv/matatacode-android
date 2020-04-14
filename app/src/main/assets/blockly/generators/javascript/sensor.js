'use strict';

goog.provide('Blockly.JavaScript.sensor');

goog.require('Blockly.JavaScript');


// If any new block imports any library, add that library name here.
Blockly.JavaScript.addReservedWords('sensor');
Blockly.JavaScript['sensor'] = function(block) {
  var dropdown_condition = block.getFieldValue('CONDITION');
  // TODO: Assemble JavaScript into code variable.
  var code = '{\"block\":\"sensorwait\",\"condition\":\"' + dropdown_condition + '\"}';
  return code;
};

Blockly.JavaScript['sensor_send_data'] = function(block) {
  var dropdown_number = block.getFieldValue('NUMBER');
  // TODO: Assemble JavaScript into code variable.
  var code = '{\"block\":\"sensorsenddata\",\"data\":\"' + dropdown_number + '\"}';
  return code;
};

Blockly.JavaScript['sensor_wait_data'] = function(block) {
  var dropdown_number = block.getFieldValue('NUMBER');
  // TODO: Assemble JavaScript into code variable.
  var code = '{\"block\":\"sensorwaitdata\",\"data\":\"' + dropdown_number + '\"}';
  return code;
};

Blockly.JavaScript['get_channel_rgb'] = function(block) {
  var dropdown_name = block.getFieldValue('NAME');
  // TODO: Assemble JavaScript into code variable.
  var code = '...';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.JavaScript.ORDER_NONE];
};

Blockly.JavaScript['get_axis'] = function(block) {
  var dropdown_name = block.getFieldValue('NAME');
  // TODO: Assemble JavaScript into code variable.
  var code = '...';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.JavaScript.ORDER_NONE];
};

Blockly.JavaScript['accelerate_check'] = function(block) {
  var dropdown_condition = block.getFieldValue('CONDITION');
  // TODO: Assemble JavaScript into code variable.
  var code = '...';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.JavaScript.ORDER_NONE];
};

Blockly.JavaScript['rgb_check'] = function(block) {
  var dropdown_condition = block.getFieldValue('CONDITION');
  // TODO: Assemble JavaScript into code variable.
  var code = '...';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.JavaScript.ORDER_NONE];
};

Blockly.JavaScript['btn_check'] = function(block) {
  var dropdown_condition = block.getFieldValue('CONDITION');
  // TODO: Assemble JavaScript into code variable.
  var code = '...';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.JavaScript.ORDER_NONE];
};

Blockly.JavaScript['sound_check'] = function(block) {
  // TODO: Assemble JavaScript into code variable.
  var code = '...';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.JavaScript.ORDER_NONE];
};

Blockly.JavaScript['bump_check'] = function(block) {
  // TODO: Assemble JavaScript into code variable.
  var code = '...';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.JavaScript.ORDER_NONE];
};



Blockly.JavaScript['data_check'] = function(block) {
  var dropdown_condition = block.getFieldValue('CONDITION');
  // TODO: Assemble JavaScript into code variable.
  var code = '...';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.JavaScript.ORDER_NONE];
};
Blockly.JavaScript['get_brightness'] = function(block) {
  // TODO: Assemble JavaScript into code variable.
  var code = '...';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.JavaScript.ORDER_NONE];
};

Blockly.JavaScript['brightness_check'] = function(block) {
  var dropdown_condition = block.getFieldValue('CONDITION');
  // TODO: Assemble JavaScript into code variable.
  var code = '...';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.JavaScript.ORDER_NONE];
};
Blockly.JavaScript['sensor_waituntil'] = function(block) {
  var value_name = Blockly.JavaScript.valueToCode(block, 'UNTIL', Blockly.JavaScript.ORDER_ATOMIC);
  // TODO: Assemble JavaScript into code variable.
  var code = '...;\n';
  return code;
};
Blockly.JavaScript['clear_check'] = function(block) {
  // TODO: Assemble JavaScript into code variable.
  var code = '...';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.JavaScript.ORDER_NONE];
};

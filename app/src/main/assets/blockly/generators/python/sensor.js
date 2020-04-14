'use strict';

goog.provide('Blockly.Python.sensor');

goog.require('Blockly.Python');


// If any new block imports any library, add that library name here.
Blockly.Python.addReservedWords('sensor');

Blockly.Python['sensor'] = function(block) {
  var dropdown_condition = block.getFieldValue('CONDITION');
  // TODO: Assemble Python into code variable.
  var code = '...\n';
  return code;
};

Blockly.Python['sensor_send_data'] = function(block) {
  var dropdown_number = block.getFieldValue('NUMBER');
  // TODO: Assemble Python into code variable.
  var code = '...\n';
  return code;
};

Blockly.Python['sensor_wait_data'] = function(block) {
  var dropdown_number = block.getFieldValue('NUMBER');
  // TODO: Assemble Python into code variable.
  var code = '...\n';
  return code;
};

Blockly.Python['get_channel_rgb'] = function(block) {
  var dropdown_name = block.getFieldValue('CHANNEL');
  // TODO: Assemble Python into code variable.
  var color;
  if (dropdown_name == 'G'){
    color = 'green';
  }else if (dropdown_name == 'R'){
    color = 'red';
  }else if (dropdown_name == 'B'){
    color = 'blue';
  }
  var code = 'matatacon.sensor.get_rgb_value('+ color +')';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.Python.ORDER_NONE];
};

Blockly.Python['get_axis'] = function(block) {
  var dropdown_name = block.getFieldValue('NAME');
  // TODO: Assemble Python into code variable.
  var code = '...';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.Python.ORDER_NONE];
};

Blockly.Python['accelerate_check'] = function(block) {
  var dropdown_condition = block.getFieldValue('CONDITION');
  // TODO: Assemble Python into code variable.
  var code = '...';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.Python.ORDER_ATOMIC];
};

Blockly.Python['rgb_check'] = function(block) {
  var dropdown_condition = block.getFieldValue('CONDITION');
  // TODO: Assemble Python into code variable.
  var code = '...';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.Python.ORDER_ATOMIC];
};

Blockly.Python['btn_check'] = function(block) {
  var dropdown_condition = block.getFieldValue('CONDITION');
  // TODO: Assemble Python into code variable.
  var code = '...';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.Python.ORDER_ATOMIC];
};

Blockly.Python['sound_check'] = function(block) {
  // TODO: Assemble Python into code variable.
  var code = '...';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.Python.ORDER_ATOMIC];
};

Blockly.Python['bump_check'] = function(block) {
  // TODO: Assemble Python into code variable.
  var code = '...';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.Python.ORDER_ATOMIC];
};

Blockly.Python['data_check'] = function(block) {
  var dropdown_condition = block.getFieldValue('CONDITION');
  // TODO: Assemble Python into code variable.
  var code = '...';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.Python.ORDER_ATOMIC];
};
Blockly.Python['get_brightness'] = function(block) {
  // TODO: Assemble Python into code variable.
  var code = '...';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.Python.ORDER_ATOMIC];
};

Blockly.Python['brightness_check'] = function(block) {
  var dropdown_condition = block.getFieldValue('CONDITION');
  // TODO: Assemble Python into code variable.
  var condition;
  if (dropdown_condition == 1){
    condition = 'bright';
  }else {
    condition = 'dark';
  }
  var code = 'matatacon.sensor.check_brightness(\"'+condition+'\")';
  // TODO: Change ORDER_NONE to the correct strength.
  var order = Blockly.Python.ORDER_FUNCTION_CALL;
  return [code, order];
};
Blockly.Python['sensor_waituntil'] = function(block) {
  var value_name = Blockly.Python.valueToCode(block, 'UNTIL', Blockly.Python.ORDER_FUNCTION_CALL);
  // TODO: Assemble Python into code variable.
  // var conditionCode = Blockly.Python.statementToCode(block, 'CONDITION');
  var code = 'matatacon.sensor.wait_until('+ value_name +')\n';
  return code;
};
Blockly.Python['clear_check'] = function(block) {
  // TODO: Assemble Python into code variable.
  var code = '...';
  // TODO: Change ORDER_NONE to the correct strength.
  return [code, Blockly.Python.ORDER_ATOMIC];
};

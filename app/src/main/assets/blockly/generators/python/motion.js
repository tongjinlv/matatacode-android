'use strict';

goog.provide('Blockly.Python.motion');

goog.require('Blockly.Python');


// If any new block imports any library, add that library name here.
Blockly.Python.addReservedWords('motion');
Blockly.Python['motion_backward'] = function(block) {
    var number_step = block.getFieldValue('STEP');
    // TODO: Assemble Python into code variable.
    var code = '03-';
    return code;
};

Blockly.Python['motion_forward'] = function(block) {
    var number_step = block.getFieldValue('STEP');
    // TODO: Assemble Python into code variable.
    var code = '00-';
    return code;
};

Blockly.Python['motion_turnleft'] = function(block) {
    var dropdown_angle = block.getFieldValue('ANGLE');
    // TODO: Assemble Python into code variable.
    var code = '01-';
    return code;
};

Blockly.Python['motion_turnright'] = function(block) {
    var dropdown_angle = block.getFieldValue('ANGLE');
    // TODO: Assemble Python into code variable.
    var code = '02-';
    return code;
};
Blockly.Python['motion_left_optimize'] = function(block) {
    var dropdown_angle = block.getFieldValue('ANGLE');
    var value_name = Blockly.Python.valueToCode(block, 'NAME', Blockly.Python.ORDER_ATOMIC);
    // TODO: Assemble Python into code variable.
    var code = '...;\n';
    return code;
};

Blockly.Python['motion_right_optimize'] = function(block) {
    var dropdown_angle = block.getFieldValue('ANGLE');
    var value_name = Blockly.Python.valueToCode(block, 'NAME', Blockly.Python.ORDER_ATOMIC);
    // TODO: Assemble Python into code variable.
    var code = '...;\n';
    return code;
};

Blockly.Python['motion_straight_optimize'] = function(block) {
    var value_name = Blockly.Python.valueToCode(block, 'NAME', Blockly.Python.ORDER_ATOMIC);
    // TODO: Assemble Python into code variable.
    var code = '...\n';
//    if value_name {
//        code = '60_01'+'_00'
//    }else{
//        code = '60_01'+'_01'
//    }
    return code;
};
Blockly.Python['motion_test_optimize'] = function(block) {
    var value_name = Blockly.Python.valueToCode(block, 'NAME', Blockly.Python.ORDER_ATOMIC);
    // TODO: Assemble Python into code variable.
    var code = '...\n';
//    if value_name {
//        code = '60_01'+'_00'
//    }else{
//        code = '60_01'+'_01'
//    }
    return code;
};

Blockly.Python['motion_length'] = function(block) {
    var dropdown_name = block.getFieldValue('DIRECTION');
    var number_name = block.getFieldValue('LENGTH');
    // TODO: Assemble Python into code variable.
    var code = '...;\n';
    return code;
  };
  
  Blockly.Python['motion_spin'] = function(block) {
    var dropdown_name = block.getFieldValue('DIRECTION');
    var angle_angel = block.getFieldValue('ANGEL');
    // TODO: Assemble Python into code variable.
    var code = '...;\n';
    return code;
  };
  
  Blockly.Python['motion_emotion'] = function(block) {
    var dropdown_name = block.getFieldValue('EMOTION');
    // TODO: Assemble Python into code variable.
    var code = '...;\n';
    return code;
  };
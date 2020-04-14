'use strict';

goog.provide('Blockly.JavaScript.loopsnew');

goog.require('Blockly.JavaScript');


// If any new block imports any library, add that library name here.
Blockly.JavaScript.addReservedWords('loopsnew');

Blockly.Python['loopsnew_count'] = function(block) {
  var dropdown_times = block.getFieldValue('TIMES');
  var statements_do1 = Blockly.Python.statementToCode(block, 'DO1');
  // TODO: Assemble Python into code variable.
  var code = '...\n';
  return code;
};

Blockly.Python['loopsnew_infi'] = function(block) {
  var statements_do1 = Blockly.Python.statementToCode(block, 'DO1');
  // TODO: Assemble Python into code variable.
  var code = '...\n';
  return code;
};

Blockly.Python['loopsnew_end'] = function(block) {
  // TODO: Assemble Python into code variable.
  var code = '...\n';
  return code;
};
Blockly.Python['loopsnew_continue'] = function(block) {
  // TODO: Assemble Python into code variable.
  var code = '...\n';
  return code;
};
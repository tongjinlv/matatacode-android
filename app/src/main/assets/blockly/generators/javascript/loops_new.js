'use strict';

goog.provide('Blockly.JavaScript.loopsnew');

goog.require('Blockly.JavaScript');


// If any new block imports any library, add that library name here.
Blockly.JavaScript.addReservedWords('loopsnew');

Blockly.JavaScript['loopsnew_count'] = function(block) {
  var dropdown_times = block.getFieldValue('TIMES');
  var statements_do1 = Blockly.JavaScript.statementToCode(block, 'DO1');
  // TODO: Assemble Python into code variable.
  var code = '{\"block\":\"loopsnew_count\",\"times\":\"' + dropdown_times + '\",\"loop\":['+ statements_do1 + ']}';
  return code;
};

Blockly.JavaScript['loopsnew_infi'] = function(block) {
  var statements_do1 = Blockly.JavaScript.statementToCode(block, 'DO1');
  // TODO: Assemble Python into code variable.
  var code = '{\"block\":\"loopsnew_infi\",\"loop\":[' + statements_do1 + ']}';
  return code;
};

Blockly.JavaScript['loopsnew_end'] = function(block) {
  // TODO: Assemble Python into code variable.
  var code = '{\"block\":\"loopsnew_end\"}';
  return code;
};
Blockly.JavaScript['loopsnew_continue'] = function(block) {
  // TODO: Assemble Python into code variable.
  var code = '{\"block\":\"loopsnew_continue\"}';
  return code;
};
'use strict';

goog.provide('Blockly.JavaScript.timer');

goog.require('Blockly.JavaScript');


// If any new block imports any library, add that library name here.
Blockly.JavaScript.addReservedWords('timer');

Blockly.JavaScript['timer_wait'] = function(block) {
    var number_second = block.getFieldValue('SECOND');
    // TODO: Assemble JavaScript into code variable.
    var code = '{\"block\":\"wait\",\"time\":\"' + number_second + '\"}';
    return code;
  };

  Blockly.JavaScript['timer_wait_ms'] = function(block) {
    var number_mstimes = block.getFieldValue('MSTIMES');
    // TODO: Assemble JavaScript into code variable.
    var code = '...;\n';
    return code;
  };
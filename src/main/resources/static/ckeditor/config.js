/**
 * @license Copyright (c) 2003-2017, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	config.height = '280';
	config.width = '920';


	config.toolbarGroups = [
		{ name: 'document', groups: [ 'mode', 'document', 'doctools' ] },
		{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
		{ name: 'clipboard', groups: [ 'clipboard', 'undo' ] },
		{ name: 'forms', groups: [ 'forms' ] },
		{ name: 'styles', groups: [ 'styles' ] },
		{ name: 'editing', groups: [ 'find', 'selection', 'spellchecker', 'editing' ] },
		{ name: 'colors', groups: [ 'colors' ] },
		{ name: 'links', groups: [ 'links' ] },
		{ name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi', 'paragraph' ] },
		{ name: 'insert', groups: [ 'insert' ] },
		{ name: 'others', groups: [ 'others' ] },
		{ name: 'tools', groups: [ 'tools' ] },
		{ name: 'about', groups: [ 'about' ] }
	];

	config.removeButtons = 'Print,Cut,Copy,Paste,PasteText,PasteFromWord,Undo,Redo,SelectAll,Scayt,Form,Checkbox,Radio,TextField,Textarea,Select,Button,ImageButton,HiddenField,CreateDiv,BidiLtr,BidiRtl,Language,Link,Unlink,Anchor,Flash,Table,Smiley,PageBreak,Iframe,ShowBlocks,Source,Save,NewPage,Preview,Templates,Italic,Underline,Strike,Subscript,Superscript,CopyFormatting,RemoveFormat,Blockquote,JustifyLeft,JustifyRight,JustifyBlock,JustifyCenter,NumberedList,BulletedList,Image';
	
	
    
};

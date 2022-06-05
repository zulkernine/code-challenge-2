import React, { Component } from 'react';
import {marked} from "marked";


const RenderMarkdown = (props) => {
    marked.setOptions({
      gfm: true,
      tables: true,
      breaks: false,
      pedantic: false,
      sanitize: true,
      smartLists: true,
      smartypants: false,
    });

    return <div dangerouslySetInnerHTML={{ __html: marked(props.text) }} />;
}
 
export default RenderMarkdown;
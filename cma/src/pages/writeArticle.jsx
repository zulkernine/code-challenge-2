import React, { Component } from "react";
import Editor from "react-markdown-editor-lite";
import "react-markdown-editor-lite/lib/index.css";
import { withRouter } from "react-router-dom";
import articleService from "../services/articleService";
import RenderMarkdown from "./components/RenderMarkDown";

class WriteArticle extends Component {
  state = {
    value: "",
  };

  constructor(props) {
    super(props);
    this.mdEditor = React.createRef();
    const article = this.props.location.state?.article;
    if (article && article.content) {
      this.state.value = article.content;
      this.state.article = article;
    } else {
      this.state.article = {};
    }
  }

  handleEditorChange = ({ html, text }) => {
    const newValue = text.replace(/\d/g, "");
    // console.log(newValue);
    this.setState({ value: newValue });
  };

  handleSave = () => {
    const { article, value } = this.state;

    articleService.writeArticle(
      value,
      article.disApproved != null ? article.id : null,
      article.disApproved != null ? article.originalArticleId : article.id
    );

    this.props.history.push("/cart");
  };

  render() {
    console.log(this.state.value);
    return (
      <div className="App col">
        <Editor
          ref={this.mdEditor}
          value={this.state.value}
          style={{
            height: "500px",
          }}
          onChange={this.handleEditorChange}
          renderHTML={(text) => <RenderMarkdown text={text} />}
        />
        <div className="row">
          <button
            onClick={this.handleSave}
            className="btn btn-primary p-5 my-5"
          >
            Save
          </button>
        </div>
      </div>
    );
  }
}

export default withRouter(WriteArticle);

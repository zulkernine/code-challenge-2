import React, { Component } from "react";
import articleService from "../services/articleService";
import RenderMarkdown from "./components/RenderMarkDown";

class articlePage extends Component {
  componentDidMount() {
    this.state.id = this.props.match.params.id;
    this.state.loading = false;
    this.loadArticle();
  }

  async loadArticle() {
    this.setState({ loading: true });

    let { data: c } = await articleService.getArticleById(this.state.id);

    this.setState({ article: c, loading: false });
    // console.log(c);
  }
  state = { id: "", article: null, loading: false,};

  render() {
    const { article, loading } = this.state;

    if (article == null || loading)
      return (
        <div className="spinner-grow text-primary" role="status">
          <span className="sr-only">Loading...</span>
        </div>
      );

    return (
      <div className="">
        <section className="p-10 overflow-hidden">
          <div className="container">
            <div className="row h6 card-header my-5">
              Author: {article.authorName}
            </div>
            <RenderMarkdown text={article.content} />
          </div>
        </section>
      </div>
    );
  }
}

export default articlePage;

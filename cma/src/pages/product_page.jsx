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
    this.setState({ loading: true, error: false });
    try {
      let response = await articleService.getArticleById(this.state.id);
      console.log(response);

      this.setState({ article: response.data });
    } catch (e) {
      console.log(e.response);
      this.setState({ error: e.response.data });
    } finally {
      this.setState({ loading: false });
    }
  }
  state = { id: "", article: null, loading: false };

  render() {
    const { article, loading, error } = this.state;
    if (!article) {
      if (loading)
        return (
          <div className="spinner-grow text-primary" role="status">
            <span className="sr-only">Loading...</span>
          </div>
        );
      else if (error)
        return (
          <div className="m-10" role="status">
            <span className="h4">Article Not found</span>
          </div>
        );
        else return <div/>
    }

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

import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import penIcon from "../../assets/icons/icons8-pen-64.png";
import articleService from "../../services/articleService";
import userService from "../../services/user.service";
import RenderMarkdown from "./RenderMarkDown";

class ItemCard extends Component {
  state = {};

  gotoArticlePage = (id) => {
    if (this.props.article.disApproved == null)
      this.props.history.push("/article/" + id);
    else this.handleEdit();
  };

  handleEdit = () => {
    this.props.history.push({
      pathname: "/write",
      state: { article: this.props.article },
    });
  };

  handleDelete = () => {
    const { article } = this.props;

    if (article.disApproved == null) articleService.deleteArticleById(article.id);
    else articleService.deleteDraftById(article.id);

    this.props.deleteItem();
  };

  render() {
    const { article } = this.props;
    const currentUID = userService.getCurrentUser()?.id;
    console.log(article);

    return (
      <div>
        <div className="card mb-3">
          <div className="card-header">{article.authorName}</div>
          <div
            className="card-body text-dark"
            onClick={() => {
              this.gotoArticlePage(article["id"]);
            }}
          >
            <RenderMarkdown text={article.content} />
          </div>
          {article.authorId == currentUID && (
            <div className="card-footer row">
              {article.disApproved != null && (
                <div className="col-sm">
                  {article.disApproved && (
                    <h6 style={{ color: "red" }}>Disapproved</h6>
                  )}
                </div>
              )}
              <div className="col-sm">
                <img
                  src={penIcon}
                  height="32"
                  width="32"
                  style={{ cursor: "pointer" }}
                  onClick={this.handleEdit}
                />
              </div>
              <div className="col-sm">
                <img
                  src="https://img.icons8.com/plasticine/100/undefined/filled-trash.png"
                  height="32"
                  width="32"
                  style={{ cursor: "pointer" }}
                  onClick={this.handleDelete}
                />
              </div>
            </div>
          )}
        </div>
      </div>
    );
  }
}

export default withRouter(ItemCard);

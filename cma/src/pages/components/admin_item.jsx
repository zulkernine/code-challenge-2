import React, { Component } from "react";
import articleService from "../../services/articleService";
import RenderMarkdown from "./RenderMarkDown";

class AdminItemCard extends Component {
  state = {};

  approve = (b) => {
    if (b) {
      articleService.approveDraft(this.props.draft.id);
    } else {
      articleService.disApproveDraft(this.props.draft.id);
    }

    this.props.removeItem();
  };

  render() {
    const { draft } = this.props;

    return (
      <div className="container">
        <div className="card bg-light mb-3">
          <div className="card-header row">
            <div className="col-sm-6 h5 text-center">{draft.authorName}</div>
            <div className="col-sm-6 ">
              <button
                type="button"
                className="btn btn-success btn-sm btn-block"
                onClick={() => this.approve(true)}
              >
                Approve
              </button>
              {draft.disApproved || (
                <button
                  type="button"
                  className="btn btn-danger btn-sm btn-block"
                  onClick={() => this.approve(false)}
                >
                  Disapprove
                </button>
              )}
            </div>
          </div>
          <div className="card-body">
            <RenderMarkdown text={draft.content} />
          </div>
        </div>
      </div>
    );
  }
}

export default AdminItemCard;

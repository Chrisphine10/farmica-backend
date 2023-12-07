import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './rework-comment.reducer';

export const ReworkCommentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const reworkCommentEntity = useAppSelector(state => state.reworkComment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="reworkCommentDetailsHeading">
          <Translate contentKey="farmicaApp.reworkComment.detail.title">ReworkComment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{reworkCommentEntity.id}</dd>
          <dt>
            <span id="comment">
              <Translate contentKey="farmicaApp.reworkComment.comment">Comment</Translate>
            </span>
          </dt>
          <dd>{reworkCommentEntity.comment}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="farmicaApp.reworkComment.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {reworkCommentEntity.createdAt ? (
              <TextFormat value={reworkCommentEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="farmicaApp.reworkComment.user">User</Translate>
          </dt>
          <dd>{reworkCommentEntity.user ? reworkCommentEntity.user.login : ''}</dd>
          <dt>
            <Translate contentKey="farmicaApp.reworkComment.reworkDetail">Rework Detail</Translate>
          </dt>
          <dd>{reworkCommentEntity.reworkDetail ? reworkCommentEntity.reworkDetail.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/rework-comment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rework-comment/${reworkCommentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ReworkCommentDetail;

import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './style.reducer';

export const StyleDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const styleEntity = useAppSelector(state => state.style.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="styleDetailsHeading">
          <Translate contentKey="farmicaApp.style.detail.title">Style</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{styleEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="farmicaApp.style.name">Name</Translate>
            </span>
          </dt>
          <dd>{styleEntity.name}</dd>
          <dt>
            <span id="grade">
              <Translate contentKey="farmicaApp.style.grade">Grade</Translate>
            </span>
          </dt>
          <dd>{styleEntity.grade}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="farmicaApp.style.code">Code</Translate>
            </span>
          </dt>
          <dd>{styleEntity.code}</dd>
          <dt>
            <Translate contentKey="farmicaApp.style.user">User</Translate>
          </dt>
          <dd>{styleEntity.user ? styleEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/style" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/style/${styleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default StyleDetail;

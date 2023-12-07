import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './region.reducer';

export const RegionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const regionEntity = useAppSelector(state => state.region.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="regionDetailsHeading">
          <Translate contentKey="farmicaApp.region.detail.title">Region</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{regionEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="farmicaApp.region.name">Name</Translate>
            </span>
          </dt>
          <dd>{regionEntity.name}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="farmicaApp.region.code">Code</Translate>
            </span>
          </dt>
          <dd>{regionEntity.code}</dd>
          <dt>
            <Translate contentKey="farmicaApp.region.user">User</Translate>
          </dt>
          <dd>{regionEntity.user ? regionEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/region" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/region/${regionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RegionDetail;

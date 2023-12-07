import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './lot-detail.reducer';

export const LotDetailDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const lotDetailEntity = useAppSelector(state => state.lotDetail.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="lotDetailDetailsHeading">
          <Translate contentKey="farmicaApp.lotDetail.detail.title">LotDetail</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{lotDetailEntity.id}</dd>
          <dt>
            <span id="lotNo">
              <Translate contentKey="farmicaApp.lotDetail.lotNo">Lot No</Translate>
            </span>
          </dt>
          <dd>{lotDetailEntity.lotNo}</dd>
          <dt>
            <Translate contentKey="farmicaApp.lotDetail.batchDetail">Batch Detail</Translate>
          </dt>
          <dd>{lotDetailEntity.batchDetail ? lotDetailEntity.batchDetail.id : ''}</dd>
          <dt>
            <Translate contentKey="farmicaApp.lotDetail.user">User</Translate>
          </dt>
          <dd>{lotDetailEntity.user ? lotDetailEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/lot-detail" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/lot-detail/${lotDetailEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LotDetailDetail;

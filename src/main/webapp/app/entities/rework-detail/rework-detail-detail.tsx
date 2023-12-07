import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './rework-detail.reducer';

export const ReworkDetailDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const reworkDetailEntity = useAppSelector(state => state.reworkDetail.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="reworkDetailDetailsHeading">
          <Translate contentKey="farmicaApp.reworkDetail.detail.title">ReworkDetail</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{reworkDetailEntity.id}</dd>
          <dt>
            <span id="uicode">
              <Translate contentKey="farmicaApp.reworkDetail.uicode">Uicode</Translate>
            </span>
          </dt>
          <dd>{reworkDetailEntity.uicode}</dd>
          <dt>
            <span id="pdnDate">
              <Translate contentKey="farmicaApp.reworkDetail.pdnDate">Pdn Date</Translate>
            </span>
          </dt>
          <dd>
            {reworkDetailEntity.pdnDate ? (
              <TextFormat value={reworkDetailEntity.pdnDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="reworkDate">
              <Translate contentKey="farmicaApp.reworkDetail.reworkDate">Rework Date</Translate>
            </span>
          </dt>
          <dd>
            {reworkDetailEntity.reworkDate ? (
              <TextFormat value={reworkDetailEntity.reworkDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="numberOfCTNs">
              <Translate contentKey="farmicaApp.reworkDetail.numberOfCTNs">Number Of CT Ns</Translate>
            </span>
          </dt>
          <dd>{reworkDetailEntity.numberOfCTNs}</dd>
          <dt>
            <span id="startCTNNumber">
              <Translate contentKey="farmicaApp.reworkDetail.startCTNNumber">Start CTN Number</Translate>
            </span>
          </dt>
          <dd>{reworkDetailEntity.startCTNNumber}</dd>
          <dt>
            <span id="endCTNNumber">
              <Translate contentKey="farmicaApp.reworkDetail.endCTNNumber">End CTN Number</Translate>
            </span>
          </dt>
          <dd>{reworkDetailEntity.endCTNNumber}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="farmicaApp.reworkDetail.status">Status</Translate>
            </span>
          </dt>
          <dd>{reworkDetailEntity.status}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="farmicaApp.reworkDetail.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {reworkDetailEntity.createdAt ? <TextFormat value={reworkDetailEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="farmicaApp.reworkDetail.warehouseDetail">Warehouse Detail</Translate>
          </dt>
          <dd>{reworkDetailEntity.warehouseDetail ? reworkDetailEntity.warehouseDetail.id : ''}</dd>
          <dt>
            <Translate contentKey="farmicaApp.reworkDetail.lotDetail">Lot Detail</Translate>
          </dt>
          <dd>{reworkDetailEntity.lotDetail ? reworkDetailEntity.lotDetail.id : ''}</dd>
          <dt>
            <Translate contentKey="farmicaApp.reworkDetail.user">User</Translate>
          </dt>
          <dd>{reworkDetailEntity.user ? reworkDetailEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/rework-detail" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rework-detail/${reworkDetailEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ReworkDetailDetail;

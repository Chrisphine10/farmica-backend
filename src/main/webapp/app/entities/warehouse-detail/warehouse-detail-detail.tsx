import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './warehouse-detail.reducer';

export const WarehouseDetailDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const warehouseDetailEntity = useAppSelector(state => state.warehouseDetail.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="warehouseDetailDetailsHeading">
          <Translate contentKey="farmicaApp.warehouseDetail.detail.title">WarehouseDetail</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{warehouseDetailEntity.id}</dd>
          <dt>
            <span id="uicode">
              <Translate contentKey="farmicaApp.warehouseDetail.uicode">Uicode</Translate>
            </span>
          </dt>
          <dd>{warehouseDetailEntity.uicode}</dd>
          <dt>
            <span id="warehouseDate">
              <Translate contentKey="farmicaApp.warehouseDetail.warehouseDate">Warehouse Date</Translate>
            </span>
          </dt>
          <dd>
            {warehouseDetailEntity.warehouseDate ? (
              <TextFormat value={warehouseDetailEntity.warehouseDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="numberOfCTNs">
              <Translate contentKey="farmicaApp.warehouseDetail.numberOfCTNs">Number Of CT Ns</Translate>
            </span>
          </dt>
          <dd>{warehouseDetailEntity.numberOfCTNs}</dd>
          <dt>
            <span id="receivedCTNs">
              <Translate contentKey="farmicaApp.warehouseDetail.receivedCTNs">Received CT Ns</Translate>
            </span>
          </dt>
          <dd>{warehouseDetailEntity.receivedCTNs}</dd>
          <dt>
            <span id="startCTNNumber">
              <Translate contentKey="farmicaApp.warehouseDetail.startCTNNumber">Start CTN Number</Translate>
            </span>
          </dt>
          <dd>{warehouseDetailEntity.startCTNNumber}</dd>
          <dt>
            <span id="endCTNNumber">
              <Translate contentKey="farmicaApp.warehouseDetail.endCTNNumber">End CTN Number</Translate>
            </span>
          </dt>
          <dd>{warehouseDetailEntity.endCTNNumber}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="farmicaApp.warehouseDetail.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {warehouseDetailEntity.createdAt ? (
              <TextFormat value={warehouseDetailEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="farmicaApp.warehouseDetail.packingZoneDetail">Packing Zone Detail</Translate>
          </dt>
          <dd>{warehouseDetailEntity.packingZoneDetail ? warehouseDetailEntity.packingZoneDetail.id : ''}</dd>
          <dt>
            <Translate contentKey="farmicaApp.warehouseDetail.lotDetail">Lot Detail</Translate>
          </dt>
          <dd>{warehouseDetailEntity.lotDetail ? warehouseDetailEntity.lotDetail.id : ''}</dd>
          <dt>
            <Translate contentKey="farmicaApp.warehouseDetail.style">Style</Translate>
          </dt>
          <dd>{warehouseDetailEntity.style ? warehouseDetailEntity.style.id : ''}</dd>
          <dt>
            <Translate contentKey="farmicaApp.warehouseDetail.user">User</Translate>
          </dt>
          <dd>{warehouseDetailEntity.user ? warehouseDetailEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/warehouse-detail" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/warehouse-detail/${warehouseDetailEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default WarehouseDetailDetail;

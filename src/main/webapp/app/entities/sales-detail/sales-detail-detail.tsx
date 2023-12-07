import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './sales-detail.reducer';

export const SalesDetailDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const salesDetailEntity = useAppSelector(state => state.salesDetail.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="salesDetailDetailsHeading">
          <Translate contentKey="farmicaApp.salesDetail.detail.title">SalesDetail</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{salesDetailEntity.id}</dd>
          <dt>
            <span id="uicode">
              <Translate contentKey="farmicaApp.salesDetail.uicode">Uicode</Translate>
            </span>
          </dt>
          <dd>{salesDetailEntity.uicode}</dd>
          <dt>
            <span id="salesDate">
              <Translate contentKey="farmicaApp.salesDetail.salesDate">Sales Date</Translate>
            </span>
          </dt>
          <dd>
            {salesDetailEntity.salesDate ? (
              <TextFormat value={salesDetailEntity.salesDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="numberOfCTNs">
              <Translate contentKey="farmicaApp.salesDetail.numberOfCTNs">Number Of CT Ns</Translate>
            </span>
          </dt>
          <dd>{salesDetailEntity.numberOfCTNs}</dd>
          <dt>
            <span id="receivedCTNs">
              <Translate contentKey="farmicaApp.salesDetail.receivedCTNs">Received CT Ns</Translate>
            </span>
          </dt>
          <dd>{salesDetailEntity.receivedCTNs}</dd>
          <dt>
            <span id="startCTNNumber">
              <Translate contentKey="farmicaApp.salesDetail.startCTNNumber">Start CTN Number</Translate>
            </span>
          </dt>
          <dd>{salesDetailEntity.startCTNNumber}</dd>
          <dt>
            <span id="endCTNNumber">
              <Translate contentKey="farmicaApp.salesDetail.endCTNNumber">End CTN Number</Translate>
            </span>
          </dt>
          <dd>{salesDetailEntity.endCTNNumber}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="farmicaApp.salesDetail.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {salesDetailEntity.createdAt ? <TextFormat value={salesDetailEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="farmicaApp.salesDetail.warehouseDetail">Warehouse Detail</Translate>
          </dt>
          <dd>{salesDetailEntity.warehouseDetail ? salesDetailEntity.warehouseDetail.id : ''}</dd>
          <dt>
            <Translate contentKey="farmicaApp.salesDetail.lotDetail">Lot Detail</Translate>
          </dt>
          <dd>{salesDetailEntity.lotDetail ? salesDetailEntity.lotDetail.id : ''}</dd>
          <dt>
            <Translate contentKey="farmicaApp.salesDetail.style">Style</Translate>
          </dt>
          <dd>{salesDetailEntity.style ? salesDetailEntity.style.id : ''}</dd>
          <dt>
            <Translate contentKey="farmicaApp.salesDetail.user">User</Translate>
          </dt>
          <dd>{salesDetailEntity.user ? salesDetailEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/sales-detail" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sales-detail/${salesDetailEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SalesDetailDetail;

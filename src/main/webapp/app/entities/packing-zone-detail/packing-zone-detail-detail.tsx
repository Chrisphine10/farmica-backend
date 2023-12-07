import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './packing-zone-detail.reducer';

export const PackingZoneDetailDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const packingZoneDetailEntity = useAppSelector(state => state.packingZoneDetail.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="packingZoneDetailDetailsHeading">
          <Translate contentKey="farmicaApp.packingZoneDetail.detail.title">PackingZoneDetail</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{packingZoneDetailEntity.id}</dd>
          <dt>
            <span id="uicode">
              <Translate contentKey="farmicaApp.packingZoneDetail.uicode">Uicode</Translate>
            </span>
          </dt>
          <dd>{packingZoneDetailEntity.uicode}</dd>
          <dt>
            <span id="pdnDate">
              <Translate contentKey="farmicaApp.packingZoneDetail.pdnDate">Pdn Date</Translate>
            </span>
          </dt>
          <dd>
            {packingZoneDetailEntity.pdnDate ? (
              <TextFormat value={packingZoneDetailEntity.pdnDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="packageDate">
              <Translate contentKey="farmicaApp.packingZoneDetail.packageDate">Package Date</Translate>
            </span>
          </dt>
          <dd>
            {packingZoneDetailEntity.packageDate ? (
              <TextFormat value={packingZoneDetailEntity.packageDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="weightReceived">
              <Translate contentKey="farmicaApp.packingZoneDetail.weightReceived">Weight Received</Translate>
            </span>
          </dt>
          <dd>{packingZoneDetailEntity.weightReceived}</dd>
          <dt>
            <span id="weightBalance">
              <Translate contentKey="farmicaApp.packingZoneDetail.weightBalance">Weight Balance</Translate>
            </span>
          </dt>
          <dd>{packingZoneDetailEntity.weightBalance}</dd>
          <dt>
            <span id="numberOfCTNs">
              <Translate contentKey="farmicaApp.packingZoneDetail.numberOfCTNs">Number Of CT Ns</Translate>
            </span>
          </dt>
          <dd>{packingZoneDetailEntity.numberOfCTNs}</dd>
          <dt>
            <span id="receivedCTNs">
              <Translate contentKey="farmicaApp.packingZoneDetail.receivedCTNs">Received CT Ns</Translate>
            </span>
          </dt>
          <dd>{packingZoneDetailEntity.receivedCTNs}</dd>
          <dt>
            <span id="startCTNNumber">
              <Translate contentKey="farmicaApp.packingZoneDetail.startCTNNumber">Start CTN Number</Translate>
            </span>
          </dt>
          <dd>{packingZoneDetailEntity.startCTNNumber}</dd>
          <dt>
            <span id="endCTNNumber">
              <Translate contentKey="farmicaApp.packingZoneDetail.endCTNNumber">End CTN Number</Translate>
            </span>
          </dt>
          <dd>{packingZoneDetailEntity.endCTNNumber}</dd>
          <dt>
            <span id="numberOfCTNsReworked">
              <Translate contentKey="farmicaApp.packingZoneDetail.numberOfCTNsReworked">Number Of CT Ns Reworked</Translate>
            </span>
          </dt>
          <dd>{packingZoneDetailEntity.numberOfCTNsReworked}</dd>
          <dt>
            <span id="numberOfCTNsSold">
              <Translate contentKey="farmicaApp.packingZoneDetail.numberOfCTNsSold">Number Of CT Ns Sold</Translate>
            </span>
          </dt>
          <dd>{packingZoneDetailEntity.numberOfCTNsSold}</dd>
          <dt>
            <span id="numberOfCTNsPacked">
              <Translate contentKey="farmicaApp.packingZoneDetail.numberOfCTNsPacked">Number Of CT Ns Packed</Translate>
            </span>
          </dt>
          <dd>{packingZoneDetailEntity.numberOfCTNsPacked}</dd>
          <dt>
            <span id="numberOfCTNsInWarehouse">
              <Translate contentKey="farmicaApp.packingZoneDetail.numberOfCTNsInWarehouse">Number Of CT Ns In Warehouse</Translate>
            </span>
          </dt>
          <dd>{packingZoneDetailEntity.numberOfCTNsInWarehouse}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="farmicaApp.packingZoneDetail.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {packingZoneDetailEntity.createdAt ? (
              <TextFormat value={packingZoneDetailEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="farmicaApp.packingZoneDetail.lotDetail">Lot Detail</Translate>
          </dt>
          <dd>{packingZoneDetailEntity.lotDetail ? packingZoneDetailEntity.lotDetail.id : ''}</dd>
          <dt>
            <Translate contentKey="farmicaApp.packingZoneDetail.style">Style</Translate>
          </dt>
          <dd>{packingZoneDetailEntity.style ? packingZoneDetailEntity.style.id : ''}</dd>
          <dt>
            <Translate contentKey="farmicaApp.packingZoneDetail.user">User</Translate>
          </dt>
          <dd>{packingZoneDetailEntity.user ? packingZoneDetailEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/packing-zone-detail" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/packing-zone-detail/${packingZoneDetailEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PackingZoneDetailDetail;

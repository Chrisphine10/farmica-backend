import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './batch-detail.reducer';

export const BatchDetailDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const batchDetailEntity = useAppSelector(state => state.batchDetail.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="batchDetailDetailsHeading">
          <Translate contentKey="farmicaApp.batchDetail.detail.title">BatchDetail</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{batchDetailEntity.id}</dd>
          <dt>
            <span id="batchNo">
              <Translate contentKey="farmicaApp.batchDetail.batchNo">Batch No</Translate>
            </span>
          </dt>
          <dd>{batchDetailEntity.batchNo}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="farmicaApp.batchDetail.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {batchDetailEntity.createdAt ? <TextFormat value={batchDetailEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="drier">
              <Translate contentKey="farmicaApp.batchDetail.drier">Drier</Translate>
            </span>
          </dt>
          <dd>{batchDetailEntity.drier}</dd>
          <dt>
            <Translate contentKey="farmicaApp.batchDetail.region">Region</Translate>
          </dt>
          <dd>{batchDetailEntity.region ? batchDetailEntity.region.id : ''}</dd>
          <dt>
            <Translate contentKey="farmicaApp.batchDetail.user">User</Translate>
          </dt>
          <dd>{batchDetailEntity.user ? batchDetailEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/batch-detail" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/batch-detail/${batchDetailEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BatchDetailDetail;

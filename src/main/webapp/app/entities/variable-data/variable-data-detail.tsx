import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './variable-data.reducer';

export const VariableDataDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const variableDataEntity = useAppSelector(state => state.variableData.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="variableDataDetailsHeading">
          <Translate contentKey="farmicaApp.variableData.detail.title">VariableData</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{variableDataEntity.id}</dd>
          <dt>
            <span id="accumulation">
              <Translate contentKey="farmicaApp.variableData.accumulation">Accumulation</Translate>
            </span>
          </dt>
          <dd>{variableDataEntity.accumulation}</dd>
          <dt>
            <span id="aiAccessCode">
              <Translate contentKey="farmicaApp.variableData.aiAccessCode">Ai Access Code</Translate>
            </span>
          </dt>
          <dd>{variableDataEntity.aiAccessCode}</dd>
        </dl>
        <Button tag={Link} to="/variable-data" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/variable-data/${variableDataEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default VariableDataDetail;

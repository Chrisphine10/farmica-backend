import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './batch-detail.reducer';

export const BatchDetail = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const batchDetailList = useAppSelector(state => state.batchDetail.entities);
  const loading = useAppSelector(state => state.batchDetail.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="batch-detail-heading" data-cy="BatchDetailHeading">
        <Translate contentKey="farmicaApp.batchDetail.home.title">Batch Details</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="farmicaApp.batchDetail.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/batch-detail/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="farmicaApp.batchDetail.home.createLabel">Create new Batch Detail</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {batchDetailList && batchDetailList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="farmicaApp.batchDetail.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('batchNo')}>
                  <Translate contentKey="farmicaApp.batchDetail.batchNo">Batch No</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('batchNo')} />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  <Translate contentKey="farmicaApp.batchDetail.createdAt">Created At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdAt')} />
                </th>
                <th className="hand" onClick={sort('drier')}>
                  <Translate contentKey="farmicaApp.batchDetail.drier">Drier</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('drier')} />
                </th>
                <th>
                  <Translate contentKey="farmicaApp.batchDetail.region">Region</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="farmicaApp.batchDetail.user">User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {batchDetailList.map((batchDetail, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/batch-detail/${batchDetail.id}`} color="link" size="sm">
                      {batchDetail.id}
                    </Button>
                  </td>
                  <td>{batchDetail.batchNo}</td>
                  <td>
                    {batchDetail.createdAt ? <TextFormat type="date" value={batchDetail.createdAt} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{batchDetail.drier}</td>
                  <td>{batchDetail.region ? <Link to={`/region/${batchDetail.region.id}`}>{batchDetail.region.id}</Link> : ''}</td>
                  <td>{batchDetail.user ? batchDetail.user.login : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/batch-detail/${batchDetail.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/batch-detail/${batchDetail.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/batch-detail/${batchDetail.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="farmicaApp.batchDetail.home.notFound">No Batch Details found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default BatchDetail;

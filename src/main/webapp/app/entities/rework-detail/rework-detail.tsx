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

import { getEntities } from './rework-detail.reducer';

export const ReworkDetail = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const reworkDetailList = useAppSelector(state => state.reworkDetail.entities);
  const loading = useAppSelector(state => state.reworkDetail.loading);

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
      <h2 id="rework-detail-heading" data-cy="ReworkDetailHeading">
        <Translate contentKey="farmicaApp.reworkDetail.home.title">Rework Details</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="farmicaApp.reworkDetail.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/rework-detail/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="farmicaApp.reworkDetail.home.createLabel">Create new Rework Detail</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {reworkDetailList && reworkDetailList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="farmicaApp.reworkDetail.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('uicode')}>
                  <Translate contentKey="farmicaApp.reworkDetail.uicode">Uicode</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('uicode')} />
                </th>
                <th className="hand" onClick={sort('pdnDate')}>
                  <Translate contentKey="farmicaApp.reworkDetail.pdnDate">Pdn Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('pdnDate')} />
                </th>
                <th className="hand" onClick={sort('reworkDate')}>
                  <Translate contentKey="farmicaApp.reworkDetail.reworkDate">Rework Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('reworkDate')} />
                </th>
                <th className="hand" onClick={sort('numberOfCTNs')}>
                  <Translate contentKey="farmicaApp.reworkDetail.numberOfCTNs">Number Of CT Ns</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('numberOfCTNs')} />
                </th>
                <th className="hand" onClick={sort('startCTNNumber')}>
                  <Translate contentKey="farmicaApp.reworkDetail.startCTNNumber">Start CTN Number</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('startCTNNumber')} />
                </th>
                <th className="hand" onClick={sort('endCTNNumber')}>
                  <Translate contentKey="farmicaApp.reworkDetail.endCTNNumber">End CTN Number</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('endCTNNumber')} />
                </th>
                <th className="hand" onClick={sort('status')}>
                  <Translate contentKey="farmicaApp.reworkDetail.status">Status</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  <Translate contentKey="farmicaApp.reworkDetail.createdAt">Created At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdAt')} />
                </th>
                <th>
                  <Translate contentKey="farmicaApp.reworkDetail.warehouseDetail">Warehouse Detail</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="farmicaApp.reworkDetail.lotDetail">Lot Detail</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="farmicaApp.reworkDetail.user">User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {reworkDetailList.map((reworkDetail, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/rework-detail/${reworkDetail.id}`} color="link" size="sm">
                      {reworkDetail.id}
                    </Button>
                  </td>
                  <td>{reworkDetail.uicode}</td>
                  <td>
                    {reworkDetail.pdnDate ? <TextFormat type="date" value={reworkDetail.pdnDate} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {reworkDetail.reworkDate ? (
                      <TextFormat type="date" value={reworkDetail.reworkDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{reworkDetail.numberOfCTNs}</td>
                  <td>{reworkDetail.startCTNNumber}</td>
                  <td>{reworkDetail.endCTNNumber}</td>
                  <td>
                    <Translate contentKey={`farmicaApp.ReworkStatus.${reworkDetail.status}`} />
                  </td>
                  <td>
                    {reworkDetail.createdAt ? <TextFormat type="date" value={reworkDetail.createdAt} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {reworkDetail.warehouseDetail ? (
                      <Link to={`/warehouse-detail/${reworkDetail.warehouseDetail.id}`}>{reworkDetail.warehouseDetail.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {reworkDetail.lotDetail ? <Link to={`/lot-detail/${reworkDetail.lotDetail.id}`}>{reworkDetail.lotDetail.id}</Link> : ''}
                  </td>
                  <td>{reworkDetail.user ? reworkDetail.user.login : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/rework-detail/${reworkDetail.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/rework-detail/${reworkDetail.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/rework-detail/${reworkDetail.id}/delete`}
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
              <Translate contentKey="farmicaApp.reworkDetail.home.notFound">No Rework Details found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ReworkDetail;

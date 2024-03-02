import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './packing-zone-detail.reducer';

export const PackingZoneDetail = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const packingZoneDetailList = useAppSelector(state => state.packingZoneDetail.entities);
  const loading = useAppSelector(state => state.packingZoneDetail.loading);
  const totalItems = useAppSelector(state => state.packingZoneDetail.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(pageLocation.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [pageLocation.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="packing-zone-detail-heading" data-cy="PackingZoneDetailHeading">
        <Translate contentKey="farmicaApp.packingZoneDetail.home.title">Packing Zone Details</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="farmicaApp.packingZoneDetail.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/packing-zone-detail/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="farmicaApp.packingZoneDetail.home.createLabel">Create new Packing Zone Detail</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {packingZoneDetailList && packingZoneDetailList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="farmicaApp.packingZoneDetail.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('uicode')}>
                  <Translate contentKey="farmicaApp.packingZoneDetail.uicode">Uicode</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('uicode')} />
                </th>
                <th className="hand" onClick={sort('pdnDate')}>
                  <Translate contentKey="farmicaApp.packingZoneDetail.pdnDate">Pdn Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('pdnDate')} />
                </th>
                <th className="hand" onClick={sort('packageDate')}>
                  <Translate contentKey="farmicaApp.packingZoneDetail.packageDate">Package Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('packageDate')} />
                </th>
                <th className="hand" onClick={sort('weightReceived')}>
                  <Translate contentKey="farmicaApp.packingZoneDetail.weightReceived">Weight Received</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('weightReceived')} />
                </th>
                <th className="hand" onClick={sort('weightBalance')}>
                  <Translate contentKey="farmicaApp.packingZoneDetail.weightBalance">Weight Balance</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('weightBalance')} />
                </th>
                <th className="hand" onClick={sort('numberOfCTNs')}>
                  <Translate contentKey="farmicaApp.packingZoneDetail.numberOfCTNs">Number Of CT Ns</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('numberOfCTNs')} />
                </th>
                <th className="hand" onClick={sort('receivedCTNs')}>
                  <Translate contentKey="farmicaApp.packingZoneDetail.receivedCTNs">Received CT Ns</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('receivedCTNs')} />
                </th>
                <th className="hand" onClick={sort('startCTNNumber')}>
                  <Translate contentKey="farmicaApp.packingZoneDetail.startCTNNumber">Start CTN Number</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('startCTNNumber')} />
                </th>
                <th className="hand" onClick={sort('endCTNNumber')}>
                  <Translate contentKey="farmicaApp.packingZoneDetail.endCTNNumber">End CTN Number</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('endCTNNumber')} />
                </th>
                <th className="hand" onClick={sort('numberOfCTNsReworked')}>
                  <Translate contentKey="farmicaApp.packingZoneDetail.numberOfCTNsReworked">Number Of CT Ns Reworked</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('numberOfCTNsReworked')} />
                </th>
                <th className="hand" onClick={sort('numberOfCTNsSold')}>
                  <Translate contentKey="farmicaApp.packingZoneDetail.numberOfCTNsSold">Number Of CT Ns Sold</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('numberOfCTNsSold')} />
                </th>
                <th className="hand" onClick={sort('numberOfCTNsPacked')}>
                  <Translate contentKey="farmicaApp.packingZoneDetail.numberOfCTNsPacked">Number Of CT Ns Packed</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('numberOfCTNsPacked')} />
                </th>
                <th className="hand" onClick={sort('numberOfCTNsInWarehouse')}>
                  <Translate contentKey="farmicaApp.packingZoneDetail.numberOfCTNsInWarehouse">Number Of CT Ns In Warehouse</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('numberOfCTNsInWarehouse')} />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  <Translate contentKey="farmicaApp.packingZoneDetail.createdAt">Created At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdAt')} />
                </th>
                <th>
                  <Translate contentKey="farmicaApp.packingZoneDetail.lotDetail">Lot Detail</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="farmicaApp.packingZoneDetail.style">Style</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="farmicaApp.packingZoneDetail.user">User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {packingZoneDetailList.map((packingZoneDetail, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/packing-zone-detail/${packingZoneDetail.id}`} color="link" size="sm">
                      {packingZoneDetail.id}
                    </Button>
                  </td>
                  <td>{packingZoneDetail.uicode}</td>
                  <td>
                    {packingZoneDetail.pdnDate ? (
                      <TextFormat type="date" value={packingZoneDetail.pdnDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {packingZoneDetail.packageDate ? (
                      <TextFormat type="date" value={packingZoneDetail.packageDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{packingZoneDetail.weightReceived}</td>
                  <td>{packingZoneDetail.weightBalance}</td>
                  <td>{packingZoneDetail.numberOfCTNs}</td>
                  <td>{packingZoneDetail.receivedCTNs}</td>
                  <td>{packingZoneDetail.startCTNNumber}</td>
                  <td>{packingZoneDetail.endCTNNumber}</td>
                  <td>{packingZoneDetail.numberOfCTNsReworked}</td>
                  <td>{packingZoneDetail.numberOfCTNsSold}</td>
                  <td>{packingZoneDetail.numberOfCTNsPacked}</td>
                  <td>{packingZoneDetail.numberOfCTNsInWarehouse}</td>
                  <td>
                    {packingZoneDetail.createdAt ? (
                      <TextFormat type="date" value={packingZoneDetail.createdAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {packingZoneDetail.lotDetail ? (
                      <Link to={`/lot-detail/${packingZoneDetail.lotDetail.id}`}>{packingZoneDetail.lotDetail.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {packingZoneDetail.style ? <Link to={`/style/${packingZoneDetail.style.id}`}>{packingZoneDetail.style.id}</Link> : ''}
                  </td>
                  <td>{packingZoneDetail.user ? packingZoneDetail.user.login : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/packing-zone-detail/${packingZoneDetail.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/packing-zone-detail/${packingZoneDetail.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/packing-zone-detail/${packingZoneDetail.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="farmicaApp.packingZoneDetail.home.notFound">No Packing Zone Details found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={packingZoneDetailList && packingZoneDetailList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default PackingZoneDetail;

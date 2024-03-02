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

import { getEntities } from './sales-detail.reducer';

export const SalesDetail = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const salesDetailList = useAppSelector(state => state.salesDetail.entities);
  const loading = useAppSelector(state => state.salesDetail.loading);
  const totalItems = useAppSelector(state => state.salesDetail.totalItems);

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
      <h2 id="sales-detail-heading" data-cy="SalesDetailHeading">
        <Translate contentKey="farmicaApp.salesDetail.home.title">Sales Details</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="farmicaApp.salesDetail.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/sales-detail/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="farmicaApp.salesDetail.home.createLabel">Create new Sales Detail</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {salesDetailList && salesDetailList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="farmicaApp.salesDetail.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('uicode')}>
                  <Translate contentKey="farmicaApp.salesDetail.uicode">Uicode</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('uicode')} />
                </th>
                <th className="hand" onClick={sort('salesDate')}>
                  <Translate contentKey="farmicaApp.salesDetail.salesDate">Sales Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('salesDate')} />
                </th>
                <th className="hand" onClick={sort('numberOfCTNs')}>
                  <Translate contentKey="farmicaApp.salesDetail.numberOfCTNs">Number Of CT Ns</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('numberOfCTNs')} />
                </th>
                <th className="hand" onClick={sort('receivedCTNs')}>
                  <Translate contentKey="farmicaApp.salesDetail.receivedCTNs">Received CT Ns</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('receivedCTNs')} />
                </th>
                <th className="hand" onClick={sort('startCTNNumber')}>
                  <Translate contentKey="farmicaApp.salesDetail.startCTNNumber">Start CTN Number</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('startCTNNumber')} />
                </th>
                <th className="hand" onClick={sort('endCTNNumber')}>
                  <Translate contentKey="farmicaApp.salesDetail.endCTNNumber">End CTN Number</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('endCTNNumber')} />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  <Translate contentKey="farmicaApp.salesDetail.createdAt">Created At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdAt')} />
                </th>
                <th>
                  <Translate contentKey="farmicaApp.salesDetail.warehouseDetail">Warehouse Detail</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="farmicaApp.salesDetail.lotDetail">Lot Detail</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="farmicaApp.salesDetail.style">Style</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="farmicaApp.salesDetail.user">User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {salesDetailList.map((salesDetail, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/sales-detail/${salesDetail.id}`} color="link" size="sm">
                      {salesDetail.id}
                    </Button>
                  </td>
                  <td>{salesDetail.uicode}</td>
                  <td>
                    {salesDetail.salesDate ? <TextFormat type="date" value={salesDetail.salesDate} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{salesDetail.numberOfCTNs}</td>
                  <td>{salesDetail.receivedCTNs}</td>
                  <td>{salesDetail.startCTNNumber}</td>
                  <td>{salesDetail.endCTNNumber}</td>
                  <td>
                    {salesDetail.createdAt ? <TextFormat type="date" value={salesDetail.createdAt} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {salesDetail.warehouseDetail ? (
                      <Link to={`/warehouse-detail/${salesDetail.warehouseDetail.id}`}>{salesDetail.warehouseDetail.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {salesDetail.lotDetail ? <Link to={`/lot-detail/${salesDetail.lotDetail.id}`}>{salesDetail.lotDetail.id}</Link> : ''}
                  </td>
                  <td>{salesDetail.style ? <Link to={`/style/${salesDetail.style.id}`}>{salesDetail.style.id}</Link> : ''}</td>
                  <td>{salesDetail.user ? salesDetail.user.login : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/sales-detail/${salesDetail.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/sales-detail/${salesDetail.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/sales-detail/${salesDetail.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="farmicaApp.salesDetail.home.notFound">No Sales Details found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={salesDetailList && salesDetailList.length > 0 ? '' : 'd-none'}>
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

export default SalesDetail;

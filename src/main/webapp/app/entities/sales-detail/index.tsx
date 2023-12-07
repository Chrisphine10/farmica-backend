import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SalesDetail from './sales-detail';
import SalesDetailDetail from './sales-detail-detail';
import SalesDetailUpdate from './sales-detail-update';
import SalesDetailDeleteDialog from './sales-detail-delete-dialog';

const SalesDetailRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<SalesDetail />} />
    <Route path="new" element={<SalesDetailUpdate />} />
    <Route path=":id">
      <Route index element={<SalesDetailDetail />} />
      <Route path="edit" element={<SalesDetailUpdate />} />
      <Route path="delete" element={<SalesDetailDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SalesDetailRoutes;

import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BatchDetail from './batch-detail';
import BatchDetailDetail from './batch-detail-detail';
import BatchDetailUpdate from './batch-detail-update';
import BatchDetailDeleteDialog from './batch-detail-delete-dialog';

const BatchDetailRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BatchDetail />} />
    <Route path="new" element={<BatchDetailUpdate />} />
    <Route path=":id">
      <Route index element={<BatchDetailDetail />} />
      <Route path="edit" element={<BatchDetailUpdate />} />
      <Route path="delete" element={<BatchDetailDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BatchDetailRoutes;

import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import VariableData from './variable-data';
import VariableDataDetail from './variable-data-detail';
import VariableDataUpdate from './variable-data-update';
import VariableDataDeleteDialog from './variable-data-delete-dialog';

const VariableDataRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<VariableData />} />
    <Route path="new" element={<VariableDataUpdate />} />
    <Route path=":id">
      <Route index element={<VariableDataDetail />} />
      <Route path="edit" element={<VariableDataUpdate />} />
      <Route path="delete" element={<VariableDataDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VariableDataRoutes;

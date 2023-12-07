import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Style from './style';
import StyleDetail from './style-detail';
import StyleUpdate from './style-update';
import StyleDeleteDialog from './style-delete-dialog';

const StyleRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Style />} />
    <Route path="new" element={<StyleUpdate />} />
    <Route path=":id">
      <Route index element={<StyleDetail />} />
      <Route path="edit" element={<StyleUpdate />} />
      <Route path="delete" element={<StyleDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StyleRoutes;

import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import StyleReport from './style-report';
import StyleReportDetail from './style-report-detail';
import StyleReportUpdate from './style-report-update';
import StyleReportDeleteDialog from './style-report-delete-dialog';

const StyleReportRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<StyleReport />} />
    <Route path="new" element={<StyleReportUpdate />} />
    <Route path=":id">
      <Route index element={<StyleReportDetail />} />
      <Route path="edit" element={<StyleReportUpdate />} />
      <Route path="delete" element={<StyleReportDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StyleReportRoutes;

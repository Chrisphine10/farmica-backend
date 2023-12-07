import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ReworkComment from './rework-comment';
import ReworkCommentDetail from './rework-comment-detail';
import ReworkCommentUpdate from './rework-comment-update';
import ReworkCommentDeleteDialog from './rework-comment-delete-dialog';

const ReworkCommentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ReworkComment />} />
    <Route path="new" element={<ReworkCommentUpdate />} />
    <Route path=":id">
      <Route index element={<ReworkCommentDetail />} />
      <Route path="edit" element={<ReworkCommentUpdate />} />
      <Route path="delete" element={<ReworkCommentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ReworkCommentRoutes;

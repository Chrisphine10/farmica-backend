import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/packing-zone-detail">
        <Translate contentKey="global.menu.entities.packingZoneDetail" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/comment">
        <Translate contentKey="global.menu.entities.comment" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/rework-comment">
        <Translate contentKey="global.menu.entities.reworkComment" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/rework-detail">
        <Translate contentKey="global.menu.entities.reworkDetail" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/lot-detail">
        <Translate contentKey="global.menu.entities.lotDetail" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/variable-data">
        <Translate contentKey="global.menu.entities.variableData" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/batch-detail">
        <Translate contentKey="global.menu.entities.batchDetail" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/region">
        <Translate contentKey="global.menu.entities.region" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/style">
        <Translate contentKey="global.menu.entities.style" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/sales-detail">
        <Translate contentKey="global.menu.entities.salesDetail" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/warehouse-detail">
        <Translate contentKey="global.menu.entities.warehouseDetail" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/farmica-report">
        <Translate contentKey="global.menu.entities.farmicaReport" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/style-report">
        <Translate contentKey="global.menu.entities.styleReport" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;

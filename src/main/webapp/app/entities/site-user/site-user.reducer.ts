import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISiteUser, defaultValue } from 'app/shared/model/site-user.model';

export const ACTION_TYPES = {
  FETCH_SITEUSER_LIST: 'siteUser/FETCH_SITEUSER_LIST',
  FETCH_SITEUSER: 'siteUser/FETCH_SITEUSER',
  CREATE_SITEUSER: 'siteUser/CREATE_SITEUSER',
  UPDATE_SITEUSER: 'siteUser/UPDATE_SITEUSER',
  DELETE_SITEUSER: 'siteUser/DELETE_SITEUSER',
  RESET: 'siteUser/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISiteUser>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type SiteUserState = Readonly<typeof initialState>;

// Reducer

export default (state: SiteUserState = initialState, action): SiteUserState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SITEUSER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SITEUSER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SITEUSER):
    case REQUEST(ACTION_TYPES.UPDATE_SITEUSER):
    case REQUEST(ACTION_TYPES.DELETE_SITEUSER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SITEUSER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SITEUSER):
    case FAILURE(ACTION_TYPES.CREATE_SITEUSER):
    case FAILURE(ACTION_TYPES.UPDATE_SITEUSER):
    case FAILURE(ACTION_TYPES.DELETE_SITEUSER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SITEUSER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_SITEUSER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SITEUSER):
    case SUCCESS(ACTION_TYPES.UPDATE_SITEUSER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SITEUSER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/site-users';

// Actions

export const getEntities: ICrudGetAllAction<ISiteUser> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SITEUSER_LIST,
    payload: axios.get<ISiteUser>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ISiteUser> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SITEUSER,
    payload: axios.get<ISiteUser>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISiteUser> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SITEUSER,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISiteUser> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SITEUSER,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISiteUser> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SITEUSER,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});

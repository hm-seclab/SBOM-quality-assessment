import axios from "axios"

const cache = new Map()

export const retrieveSbomMetadata = async () => {
    const endpoint = '/api/getSbomMetadata'
    const result = await retrieveData(endpoint)

    for (const item of result) {
        if (!item.spdx_exists) {
            item.status = 'failed'
        } else if (item.totalPackages < 2) {
            item.status = 'corrupted'
        } else {
            item.status = 'good'
        }
    }

    return retrieveData(endpoint)
}

export const retrieveSbomMetadataByProject= async (project_id) => {
    const endpoint = '/api/getSbomMetadataByProject?project_id=' + project_id
    return retrieveData(endpoint)
}

export const retrieveSbomMetadataByFile = async (sbom_file_id) => {
    const endpoint = '/api/getSbomMetadataByFile?project_id=' + sbom_file_id
    return retrieveData(endpoint)
}

export const retrieveDependencyListByName = async (project_id) => {
    const endpoint = '/api/getDependencyListByName?project_id=' + project_id
    return retrieveData(endpoint)
}

export const retrieveDependencyListByNameVersion = async (project_id) => {
    const endpoint = '/api/getDependencyListByNameVersion?project_id=' + project_id
    return retrieveData(endpoint)
}

export const retrieveDependencyListByRefLocCompressed = async (project_id) => {
    const endpoint = '/api/getDependencyListByRefLocCompressed?project_id=' + project_id
    return retrieveData(endpoint)
}

export const retrieveDependencyListByRefLocCompressedVersion = async (project_id) => {
    const endpoint = '/api/getDependencyListByRefLocCompressedVersion?project_id=' + project_id
    return retrieveData(endpoint)
}

export const retrieveDependencyListByRefLoc = async (project_id) => {
    const endpoint = '/api/getDependencyListByRefLoc?project_id=' + project_id
    return retrieveData(endpoint)
}

export const retrieveDependencyListByRefLocVersion = async (project_id) => {
    const endpoint = '/api/getDependencyListByRefLocVersion?project_id=' + project_id
    return retrieveData(endpoint)
}

export const retrieveCdxDependencyListByName = async (project_id) => {
    const endpoint = '/api/getCdxDependencyListByName?project_id=' + project_id
    return retrieveData(endpoint)
}

export const retrieveCdxDependencyListByNameVersion = async (project_id) => {
    const endpoint = '/api/getCdxDependencyListByNameVersion?project_id=' + project_id
    return retrieveData(endpoint)
}

export const retrieveCdxDependencyListByPurl = async (project_id) => {
    const endpoint = '/api/getCdxDependencyListByPurl?project_id=' + project_id
    return retrieveData(endpoint)
}

export const retrieveCdxDependencyListByPurlVersion = async (project_id) => {
    const endpoint = '/api/getCdxDependencyListByPurlVersion?project_id=' + project_id
    return retrieveData(endpoint)
}

export const retrieveSpdxInsightsForFile = async (sbom_file_id) => {
    const endpoint = '/api/getSpdxInsightsForFile?sbom_file_id=' + sbom_file_id
    return retrieveData(endpoint)
}

export const retrieveSpdxInsightsForProject = async (project_id) => {
    const endpoint = '/api/getSpdxInsightsForProject?project_id=' + project_id
    return retrieveData(endpoint)
}

export const retrieveLiceseList = async (project_id, attribute) => {
    const endpoint = '/api/getLicenseList?project_id=' + project_id + "&attribute=" + attribute
    return retrieveData(endpoint)
}

const retrieveData = async (endpoint) => {
    try {
        if (cache.has(endpoint)) {
            return cache.get(endpoint)
        } else {
            const response = await axios({
                method: 'get',
                headers: {'Content-Type': 'application/json'},
                url: endpoint
            })
            cache.set(endpoint, response.data)
            return response.data
        }
    } catch (error) {
        console.error(error)
        throw new Error("Failed to retrieve data " + endpoint)
    }
}
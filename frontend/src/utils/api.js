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

export const retrieveDependencyList = async (project_id, type) => {
    const endpoint = '/api/getDependencyList?project_id=' + project_id + '&type=' + type
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
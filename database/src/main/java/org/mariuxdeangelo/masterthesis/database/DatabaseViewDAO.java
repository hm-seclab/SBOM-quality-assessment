package org.mariuxdeangelo.masterthesis.database;

import org.mariuxdeangelo.masterthesis.database.modelsView.*;
import org.mariuxdeangelo.masterthesis.database.rowMapperView.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseViewDAO extends AbstractPostgresDAO {

    public void insertApiUsage(EndpointType endpointType) {
        String sql = "INSERT INTO api_usage (endpoint) VALUES (?)";
        jdbcTemplate.update(sql, endpointType.toString());
    }

    public List<SbomMetadataViewModel> retrieveSbomMetadata() {
        String sql = "SELECT sp.project_id, sp.name, sp.container, sp.git, " +
                "       sbom_file_id, generator, mode, timestamp, spdx_orig, cdx_orig, command, execution_time, " +
                "       shell_out IS NOT NULL as log_exists, spdx IS NOT NULL as spdx_exists, cdx IS NOT NULL as cdx_exists, " +
                "       jsonb_array_length(spdx -> 'packages') as total_packages, " +
                "       jsonb_array_length(spdx -> 'relationships') as total_relationships, " +
                "       jsonb_array_length(spdx -> 'hasExtractedLicensingInfos') as total_hasexternallicensinginfos " +
                "FROM sbom_files " +
                "JOIN subject_projects sp on sbom_files.project_id = sp.project_id " +
                "ORDER BY sbom_file_id ASC";
        return jdbcTemplate.query(sql, new RowMapperSbomMetadata());

    }

    public List<SbomMetadataViewModel> retrieveSbomMetadataByProject(long project_id) {
        String sql = "SELECT sp.project_id, sp.name, sp.container, sp.git, " +
                     "sbom_file_id, generator, mode, timestamp, spdx_orig, cdx_orig, command, execution_time, " +
                     "shell_out IS NOT NULL as log_exists, spdx IS NOT NULL as spdx_exists, cdx IS NOT NULL as cdx_exists, " +
                     "jsonb_array_length(spdx -> 'packages') as total_packages, " +
                     "jsonb_array_length(spdx -> 'relationships') as total_relationships, " +
                     "jsonb_array_length(spdx -> 'hasExtractedLicensingInfos') as total_hasexternallicensinginfos " +
                     "FROM sbom_files " +
                     "JOIN subject_projects sp on sbom_files.project_id = sp.project_id " +
                     "WHERE sp.project_id = ? " +
                     "ORDER BY mode, generator ASC";
        return jdbcTemplate.query(sql, new RowMapperSbomMetadata(), project_id);
    }

    public SbomMetadataViewModel retrieveSbomMetadataByFile(long sbom_file_id) {
        String sql = "SELECT sp.project_id, sp.name, sp.container, sp.git, " +
                     "sbom_file_id, generator, mode, timestamp, spdx_orig, cdx_orig, command, execution_time, " +
                     "shell_out IS NOT NULL as log_exists, spdx IS NOT NULL as spdx_exists, cdx IS NOT NULL as cdx_exists, " +
                     "jsonb_array_length(spdx -> 'packages') as total_packages, " +
                     "jsonb_array_length(spdx -> 'relationships') as total_relationships, " +
                     "jsonb_array_length(spdx -> 'hasExtractedLicensingInfos') as total_hasexternallicensinginfos " +
                     "FROM sbom_files " +
                     "JOIN subject_projects sp on sbom_files.project_id = sp.project_id " +
                     "WHERE sbom_file_id = ?";
        return jdbcTemplate.queryForObject(sql, new RowMapperSbomMetadata(), sbom_file_id);
    }

    public SbomDownloadViewModel retrieveSbomDownload(long sbom_file_id) {
        String sql = "SELECT subject_projects.project_id, sbom_file_id, name, generator, mode, timestamp, spdx_orig, spdx, cdx_orig, cdx, command, shell_out " +
                "FROM subject_projects " +
                "JOIN sbom_files sf on subject_projects.project_id = sf.project_id " +
                "WHERE sbom_file_id = ?";
        return jdbcTemplate.queryForObject(sql, new RowMapperSbomDownload(), sbom_file_id);
    }

    public List<DependencyListViewModel> retrieveDependencyListByName(long project_id) {
        String sql = "SELECT LOWER(package ->> 'name') as package_name, ARRAY_AGG(DISTINCT result) as generator_list " +
                     "FROM (SELECT jsonb_array_elements(spdx -> 'packages') as package, CONCAT(generator, '-', mode) as result " +
                     "      FROM sbom_files " +
                     "      WHERE project_id = ?) as adsf " +
                     "GROUP BY package_name " +
                     "ORDER BY package_name";
        return jdbcTemplate.query(sql, new RowMapperDependencyList(), project_id);
    }

    public List<DependencyListViewModel> retrieveDependencyListByNameVersion(long project_id) {
        String sql = "SELECT LOWER(CONCAT(package ->> 'name', '-', package ->> 'versionInfo')) as package_name, ARRAY_AGG(DISTINCT result) as generator_list " +
                     "FROM (SELECT jsonb_array_elements(spdx -> 'packages') as package, CONCAT(generator, '-', mode) as result " +
                     "      FROM sbom_files " +
                     "      WHERE project_id = ?) as asdf " +
                     "GROUP BY package_name " +
                     "ORDER BY package_name";
        return jdbcTemplate.query(sql, new RowMapperDependencyList(), project_id);
    }

    public List<DependencyListViewModel> retrieveDependencyListByRefLoc(long project_id) {
        String sql = "SELECT split_part(referenceLocator, '@', 1) as package_name, ARRAY_AGG(DISTINCT result) as generator_list " +
                     "FROM (SELECT jsonb_array_elements(package -> 'externalRefs') ->> 'referenceLocator' as referenceLocator, result " +
                     "      FROM (SELECT jsonb_array_elements(spdx -> 'packages') as package, CONCAT(generator, '-', mode) as result " +
                     "            FROM sbom_files " +
                     "            WHERE project_id = ?) as asdf " +
                     "     ) as result_query " +
                     "WHERE referenceLocator not like 'cpe%' " +
                     "GROUP BY package_name " +
                     "ORDER BY package_name";
        return jdbcTemplate.query(sql, new RowMapperDependencyList(), project_id);
    }

    public List<DependencyListViewModel> retrieveDependencyListByRefLocVersion(long project_id) {
        String sql = "SELECT split_part(referenceLocator, '?', 1) as package_name, ARRAY_AGG(DISTINCT result) as generator_list " +
                     "FROM (SELECT jsonb_array_elements(package -> 'externalRefs') ->> 'referenceLocator' as referenceLocator, result " +
                     "      FROM (SELECT jsonb_array_elements(spdx -> 'packages') as package, CONCAT(generator, '-', mode) as result " +
                     "            FROM sbom_files " +
                     "            WHERE project_id = ?) as asdf " +
                     ") as result_query " +
                     "WHERE referenceLocator not like 'cpe%' " +
                     "GROUP BY package_name " +
                     "ORDER BY package_name;";
        return jdbcTemplate.query(sql, new RowMapperDependencyList(), project_id);
    }

    public List<DependencyListViewModel> retrieveCdxDependencyListByName(long project_id) {
        String sql = "SELECT package ->> 'name' as package_name, ARRAY_AGG(DISTINCT result) as generator_list " +
                     "FROM (SELECT jsonb_array_elements(cdx -> 'components') as package, CONCAT(generator, '-', mode) as result " +
                     "      FROM sbom_files " +
                     "      WHERE project_id = ?) as adsf " +
                     "GROUP BY package_name " +
                     "ORDER BY package_name";
        return jdbcTemplate.query(sql, new RowMapperDependencyList(), project_id);
    }

    public List<DependencyListViewModel> retrieveCdxDependencyListByNameWithVersion(long project_id) {
        String sql = "SELECT CONCAT(package ->> 'name', ':', package ->> 'version') as package_name, ARRAY_AGG(DISTINCT result) as generator_list " +
                     "FROM (SELECT jsonb_array_elements(cdx -> 'components') as package, CONCAT(generator, '-', mode) as result " +
                     "      FROM sbom_files " +
                     "      WHERE project_id = ?) as adsf " +
                     "GROUP BY package_name " +
                     "ORDER BY package_name";
        return jdbcTemplate.query(sql, new RowMapperDependencyList(), project_id);
    }

    public List<DependencyListViewModel> retrieveCdxDependencyListByPurl(long project_id) {
        String sql = "SELECT SPLIT_PART(package ->> 'purl', '@', 1) as package_name, ARRAY_AGG(DISTINCT result) as generator_list " +
                     "FROM (SELECT jsonb_array_elements(cdx -> 'components') as package, CONCAT(generator, '-', mode) as result " +
                     "      FROM sbom_files " +
                     "      WHERE project_id = ?) as adsf " +
                     "WHERE package ->> 'purl' is not null " +
                     "GROUP BY package_name " +
                     "ORDER BY package_name";
        return jdbcTemplate.query(sql, new RowMapperDependencyList(), project_id);
    }

    public List<DependencyListViewModel> retrieveCdxDependencyListByPurlWithVersion(long project_id) {
        String sql = "SELECT package ->> 'purl' as package_name, ARRAY_AGG(DISTINCT result) as generator_list " +
                     "FROM (SELECT jsonb_array_elements(cdx -> 'components') as package, CONCAT(generator, '-', mode) as result " +
                     "      FROM sbom_files " +
                     "      WHERE project_id = ?) as adsf " +
                     "WHERE package ->> 'purl' is not null " +
                     "GROUP BY package_name " +
                     "ORDER BY package_name";
        return jdbcTemplate.query(sql, new RowMapperDependencyList(), project_id);
    }

    public List<LicenseListViewModel> retrieveLicenseList(long project_id, String attribute) {
        if (attribute.equals("copyrightText")
                || attribute.equals("filesAnalyzed")
                || attribute.equals("licenseDeclared")
                || attribute.equals("downloadLocation")
                || attribute.equals("licenseConcluded")) {
            // continue
        } else {
            throw new UnsupportedOperationException("Der Type " + attribute + " ist nicht zugelassen");
        }
        String sql = "SELECT count(asdf.package) as amount, " +
                     "       asdf.package ->> ? as license, " +
                     "       result as generator, " +
                     "       ARRAY_AGG(DISTINCT CONCAT(package ->> 'name', '-', package ->> 'versionInfo')) as package_list " +
                     "FROM (SELECT jsonb_array_elements(spdx -> 'packages') as package, CONCAT(generator, '-', mode) as result " +
                     "      FROM sbom_files " +
                     "      WHERE project_id = ? AND spdx is not null) as asdf " +
                     "GROUP BY result, license " +
                     "ORDER BY result";
        return jdbcTemplate.query(sql, new RowMapperLicenseList(), attribute, project_id);
    }

    public List<SpdxInsightsModel> retrieveSpdxInsightsByProject(long project_id) {
        String sql = "SELECT sf.sbom_file_id, generator, mode, " +
                "       spdx ->> 'name' as name, " +
                "       spdx ->> 'SPDXID' as spdxid, " +
                "       spdx ->> 'comment' as comment, " +
                "       spdx ->> 'dataLicense' as dataLicense, " +
                "       spdx ->> 'spdxVersion' as spdxVersion, " +
                "       spdx -> 'creationInfo' ->> 'created' as creationDate, " +
                "       spdx -> 'creationInfo' ->> 'licenseListVersion' as licenseListVersion, " +
                "       spdx -> 'creationInfo' -> 'creators' as creators, " +
                "       spdx ->> 'documentNamespace' as documentNamespace, " +
                "       jsonb_array_length(spdx -> 'packages') as total_packages, " +
                "       jsonb_array_length(spdx -> 'relationships') as total_relationships, " +
                "       jsonb_array_length(spdx -> 'hasExtractedLicensingInfos') as total_hasexternallicensinginfos, " +
                "       total_supplier, total_package_name, total_package_version_info, total_spdxid " +
                "FROM (SELECT count(supplier)        as total_supplier, " +
                "             count(package_name)    as total_package_name, " +
                "             count(package_version) as total_package_version_info, " +
                "             count(spdxid)          as total_spdxid, " +
                "             sbom_file_id " +
                "      FROM (SELECT jsonb_array_elements(spdx -> 'packages') ->> 'supplier'    as supplier, " +
                "                   jsonb_array_elements(spdx -> 'packages') ->> 'name'        as package_name, " +
                "                   jsonb_array_elements(spdx -> 'packages') ->> 'versionInfo' as package_version, " +
                "                   jsonb_array_elements(spdx -> 'packages') ->> 'SPDXID'      as spdxid, " +
                "                   sbom_file_id " +
                "            FROM sbom_files " +
                "            WHERE project_id = ?) as innereresd " +
                "      GROUP BY sbom_file_id) as innerer " +
                "         FULL OUTER JOIN (SELECT * FROM sbom_files WHERE project_id = ? AND spdx is not null) sf " +
                "                         on sf.sbom_file_id = innerer.sbom_file_id";
        return jdbcTemplate.query(sql, new RowMapperSpdxInsights(), project_id, project_id);
    }

    public SpdxInsightsModel retrieveSpdxInsightsByFile(long sbom_file_id) {
        String sql = "SELECT sf.sbom_file_id, generator, mode, " +
                "       spdx ->> 'name' as name, " +
                "       spdx ->> 'SPDXID' as spdxid, " +
                "       spdx ->> 'comment' as comment, " +
                "       spdx ->> 'dataLicense' as dataLicense, " +
                "       spdx ->> 'spdxVersion' as spdxVersion, " +
                "       spdx -> 'creationInfo' ->> 'created' as creationDate, " +
                "       spdx -> 'creationInfo' ->> 'licenseListVersion' as licenseListVersion, " +
                "       spdx -> 'creationInfo' -> 'creators' as creators, " +
                "       spdx ->> 'documentNamespace' as documentNamespace, " +
                "       jsonb_array_length(spdx -> 'packages') as total_packages, " +
                "       jsonb_array_length(spdx -> 'relationships') as total_relationships, " +
                "       jsonb_array_length(spdx -> 'hasExtractedLicensingInfos') as total_hasexternallicensinginfos, " +
                "       total_supplier, total_package_name, total_package_version_info, total_spdxid " +
                "FROM (SELECT count(supplier)        as total_supplier, " +
                "             count(package_name)    as total_package_name, " +
                "             count(package_version) as total_package_version_info, " +
                "             count(spdxid)          as total_spdxid, " +
                "             sbom_file_id " +
                "      FROM (SELECT jsonb_array_elements(spdx -> 'packages') ->> 'supplier'    as supplier, " +
                "                   jsonb_array_elements(spdx -> 'packages') ->> 'name'        as package_name, " +
                "                   jsonb_array_elements(spdx -> 'packages') ->> 'versionInfo' as package_version, " +
                "                   jsonb_array_elements(spdx -> 'packages') ->> 'SPDXID'      as spdxid, " +
                "                   sbom_file_id " +
                "            FROM sbom_files " +
                "            WHERE sbom_file_id = ?) as innereresd " +
                "      GROUP BY sbom_file_id) as innerer " +
                "         FULL OUTER JOIN (SELECT * FROM sbom_files WHERE sbom_file_id = ? AND spdx is not null) sf " +
                "                         on sf.sbom_file_id = innerer.sbom_file_id";
        return jdbcTemplate.queryForObject(sql, new RowMapperSpdxInsights(), sbom_file_id, sbom_file_id);
    }
}


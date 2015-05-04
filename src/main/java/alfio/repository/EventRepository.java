/**
 * This file is part of alf.io.
 *
 * alf.io is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * alf.io is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with alf.io.  If not, see <http://www.gnu.org/licenses/>.
 */
package alfio.repository;

import alfio.datamapper.AutoGeneratedKey;
import alfio.datamapper.Bind;
import alfio.datamapper.Query;
import alfio.datamapper.QueryRepository;
import alfio.model.Event;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@QueryRepository
public interface EventRepository {

    @Query("select * from event where id = :eventId")
    Event findById(@Bind("eventId") int eventId);

    @Query("select * from event where short_name = :eventName")
    Event findByShortName(@Bind("eventName") String eventName);

    @Query("select * from event order by start_ts asc")
    List<Event> findAll();

    @Query("select * from event where org_id = :organizationId")
    List<Event> findByOrganizationId(@Bind("organizationId") int organizationId);

    @Query("insert into event(description, short_name, website_url, website_t_c_url, image_url, file_blob_id, location, latitude, longitude, start_ts, end_ts, time_zone, regular_price_cts, currency, available_seats, vat_included, vat, allowed_payment_proxies, private_key, org_id) " +
            "values(:description, :shortName, :websiteUrl, :termsUrl, :imageUrl, :fileBlobId, :location, :latitude, :longitude, :start_ts, :end_ts, :time_zone, :regular_price, :currency, :available_seats, :vat_included, :vat, :paymentProxies, :privateKey, :organizationId)")
    @AutoGeneratedKey("id")
    Pair<Integer, Integer> insert(@Bind("description") String description, @Bind("shortName") String shortName, @Bind("websiteUrl") String websiteUrl, @Bind("termsUrl") String termsUrl, @Bind("imageUrl") String imageUrl, @Bind("fileBlobId") String fileBlobId,
               @Bind("location") String location, @Bind("latitude") String latitude, @Bind("longitude") String longitude, @Bind("start_ts") ZonedDateTime begin,
               @Bind("end_ts") ZonedDateTime end, @Bind("time_zone") String timeZone, @Bind("regular_price") int regular_price, @Bind("currency") String currency,
               @Bind("available_seats") int available_seats, @Bind("vat_included") boolean vat_included,
               @Bind("vat") BigDecimal vat, @Bind("paymentProxies") String allowedPaymentProxies,
               @Bind("privateKey") String privateKey,
               @Bind("organizationId") int orgId);

    @Query("update event set description = :description, short_name = :shortName, website_url = :websiteUrl, website_t_c_url = :termsUrl, image_url = :imageUrl, file_blob_id = :fileBlobId, location = :location, latitude = :latitude, longitude = :longitude, start_ts = :start_ts, end_ts = :end_ts, time_zone = :time_zone, org_id = :organizationId where id = :id")
    int updateHeader(@Bind("id") int id, @Bind("description") String description, @Bind("shortName") String shortName, @Bind("websiteUrl") String websiteUrl, @Bind("termsUrl") String termsUrl, @Bind("imageUrl") String imageUrl, @Bind("fileBlobId") String fileBlobId,
                     @Bind("location") String location, @Bind("latitude") String latitude, @Bind("longitude") String longitude, @Bind("start_ts") ZonedDateTime begin,
                     @Bind("end_ts") ZonedDateTime end, @Bind("time_zone") String timeZone, @Bind("organizationId") int organizationId);

    @Query("update event set regular_price_cts = :regular_price, currency = :currency, available_seats = :available_seats, vat_included = :vat_included, vat = :vat, allowed_payment_proxies = :paymentProxies where id = :eventId")
    int updatePrices(@Bind("regular_price") int regular_price, @Bind("currency") String currency,
                     @Bind("available_seats") int available_seats, @Bind("vat_included") boolean vat_included,
                     @Bind("vat") BigDecimal vat, @Bind("paymentProxies") String allowedPaymentProxies, @Bind("eventId") int eventId);

    /**
     * TODO check: This one is kinda ugly.
     * */
    @Query("select * from event where id = (select event_id from ticket where tickets_reservation_id = :reservationId limit 1)")
	Event findByReservationId(@Bind("reservationId") String reservationId);

}
